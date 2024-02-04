package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyCharterAction;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.FlyFromResearchCenterAnywhereAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnExecuteDTO;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.interfaces.ITurnService;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dataontheroad.pandemic.actions.ActionsType.FLYCHARTER;
import static com.dataontheroad.pandemic.actions.ActionsType.OPERATION_FLY;
import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static com.dataontheroad.pandemic.game.ActionServiceHelper.getListOfActions;
import static com.dataontheroad.pandemic.game.ActionServiceHelper.getListOfSpecialActions;
import static com.dataontheroad.pandemic.game.TurnServiceHelper.*;
import static java.util.Objects.isNull;

@Service
public class TurnServiceImpl implements ITurnService {

    private final GamePersistenceOnHashMap gamePersistence;

    private final InfectionServiceImpl infectionService;

    private final EpidemicServiceImpl epidemicService;

    public TurnServiceImpl(GamePersistenceOnHashMap gamePersistence,
                           InfectionServiceImpl infectionService,
                           EpidemicServiceImpl epidemicService) {
        this.gamePersistence = gamePersistence;
        this.infectionService = infectionService;
        this.epidemicService = epidemicService;
    }

    @Override
    public TurnResponseDTO getTurnServiceInformation(UUID gameId) {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);
        TurnResponseDTO turnResponseDTO = new TurnResponseDTO();

        if (!isNull(gameDTO)) {
            Player player = gameDTO.getTurnInformation().getActivePlayer();
            List<Action> actionList = getListOfActions(player,
                    gameDTO.getBoard().getVirusList(),
                    getCitiesWithResearchCenter(gameDTO),
                    getOtherPlayersOnTheCity(gameDTO),
                    gameDTO.getBoard().getBoardCities());

            actionList.addAll(getListOfSpecialActions(player, gameDTO.getBoard().getPlayers(), new ArrayList<>(gameDTO.getBoard().getPlayerDiscardDeck())));
            turnResponseDTO.setTurnInformation(gameDTO.getTurnInformation(), actionList);
            return turnResponseDTO;
        } else {
            return null;
        }
    }

    @Override
    public Action getSelectedAction(TurnExecuteDTO turnExecuteDTO, int actionPosition) throws GameExecutionException {

        List<Action> actionList = getListOfActions(turnExecuteDTO.getActivePlayer(),
                turnExecuteDTO.getVirusList(),
                turnExecuteDTO.getResearchCenter(),
                turnExecuteDTO.getOtherPlayersOnTheCity(),
                turnExecuteDTO.getBoardCities());
        actionList.addAll(getListOfSpecialActions(turnExecuteDTO.getActivePlayer(),
                turnExecuteDTO.getBoardPlayers(),
                turnExecuteDTO.getDiscardDeck()));

        return actionList.get(actionPosition);
    }

    @Override
    public TurnInformation executeAction(UUID gameId, Action action) throws ActionException, GameExecutionException, EndOfGameException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);

        if (isNull(gameDTO)) {
            throw new GameExecutionException(GAME_NOT_FOUND);
        }

        action.execute();
        if (!gameDTO.getTurnInformation().canDoNextActionAndReduceMissingTurns()) {

            // gets the cards from the player queue where could appear EPIDEMIC, CITY or ACTION cards
            if (epidemicService.playerGetNewCardsIfIsNotEpidemicAsTimesAsManyTimesAsInfectionsCards(gameDTO.getBoard().getPlayerQueue(), gameDTO.getTurnInformation().getActivePlayer(), gameDTO.getBoard().getNumberInfectionCard())) {
                City cityToInfect = infectionService.getCardFromBottomInfectionDesk(gameDTO.getBoard().getInfectionDeck(), gameDTO.getBoard().getInfectionDiscardDeck());
                infectCityIfPosible(gameDTO, cityToInfect);
            }

            // gets the cards from the infection queue where could appear only city cards
            int numberCardToInfect = gameDTO.getBoard().getNumberInfectionCard();
            for(int i=0; i < numberCardToInfect; i++) {
                City cityToInfect = infectionService.getCardFromTopInfectionDesk(gameDTO.getBoard().getInfectionDeck(), gameDTO.getBoard().getInfectionDiscardDeck());
                infectCityIfPosible(gameDTO, cityToInfect);
            }

            Player player = getNextActivePlayer(gameDTO.getBoard().getPlayers(),
                    gameDTO.getTurnInformation().getActivePlayer());
            gameDTO.getTurnInformation().setNewTurn(player);
        }
        gamePersistence.insertOrUpdateGame(gameDTO);

        return gameDTO.getTurnInformation();
    }

    private void infectCityIfPosible(GameDTO gameDTO, City cityToInfect) throws EndOfGameException {
        if (infectionService.canCityBeInfected(cityToInfect, gameDTO.getBoard().getVirusList(), gameDTO.getBoard().getPlayers())) {
            VirusType virusTypeToOutbreak = infectionService.infectCityAndReturnCityVirusTypeIfOverpassOutbreak(gameDTO.getBoard().getCityFromBoardList(cityToInfect));
            if (!isNull(virusTypeToOutbreak)) {
                infectionService.spreadOutbreak(gameDTO.getBoard().getPlayers(), cityToInfect.getNodeCityConnection());
                gameDTO.getBoard().increaseOutBreaks();
            }
        }
    }

    @Override
    public Action actionFormatValidation(TurnExecuteDTO turnExecuteDTO, Action action, HashMap<String, String> additionalFields) throws GameExecutionException {

        if(FLYCHARTER.equals(action.getActionsType())) {
            if(!isNull(additionalFields) && additionalFields.containsKey(ADDITIONAL_FIELD_DESTINATION)) {
                City city = turnExecuteDTO.getBoard().getCityFromBoardList(new City(additionalFields.get(ADDITIONAL_FIELD_DESTINATION), null));
                if(isNull(city)) {
                    throw new GameExecutionException(TURN_WRONG_FLYCHARTER_INVALID_DESTINATION_CITY);
                }
                ((FlyCharterAction) action).setDestination(city);
            } else {
                throw new GameExecutionException(TURN_WRONG_FLYCHARTER_DESTINATION_FIELD);
            }
        } else if(OPERATION_FLY.equals(action.getActionsType())) {
            if(!isNull(additionalFields) && additionalFields.containsKey(ADDITIONAL_FIELD_DESTINATION)) {
                City city = turnExecuteDTO.getBoard().getCityFromBoardList(new City(additionalFields.get(ADDITIONAL_FIELD_DESTINATION), null));
                if(isNull(city)) {
                    throw new GameExecutionException(TURN_WRONG_OPERATION_INVALID_DESTINATION_CITY);
                }
                ((FlyFromResearchCenterAnywhereAction) action).setDestination(city);
            } else {
                throw new GameExecutionException(TURN_WRONG_OPERATION_DESTINATION_FIELD);
            }
        }
        return action;
    }

    @Override
    public TurnExecuteDTO getTurnExecuteDTO(UUID gameId) throws GameExecutionException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);
        if (isNull(gameDTO)) {
            throw new GameExecutionException(GAME_NOT_FOUND);
        }
        return new TurnExecuteDTO(gameDTO);
    }

}

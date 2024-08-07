package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyCharterAction;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.FlyFromResearchCenterAnywhereAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnExecuteDTO;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceAbstractClass;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.interfaces.ITurnService;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.dataontheroad.pandemic.actions.ActionsType.FLYCHARTER;
import static com.dataontheroad.pandemic.actions.ActionsType.OPERATION_FLY;
import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static com.dataontheroad.pandemic.game.ActionServiceHelper.getListOfActions;
import static com.dataontheroad.pandemic.game.ActionServiceHelper.getListOfSpecialActions;
import static com.dataontheroad.pandemic.game.TurnServiceHelper.getCitiesWithResearchCenter;
import static com.dataontheroad.pandemic.game.TurnServiceHelper.getOtherPlayersOnTheCity;
import static java.util.Objects.isNull;

@Service
public class TurnServiceImpl implements ITurnService {

    private final GamePersistenceAbstractClass gamePersistence;

    private final EndOfTurnServiceImpl endOfTurnService;

    private final EndOfGameServiceImpl endOfGameService;

    public TurnServiceImpl(@Qualifier("GamePersistenceOnFile") GamePersistenceAbstractClass gamePersistence,
                           EndOfTurnServiceImpl endOfTurnService,
                           EndOfGameServiceImpl endOfGameService) {
        this.gamePersistence = gamePersistence;
        this.endOfTurnService = endOfTurnService;
        this.endOfGameService = endOfGameService;
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
            endOfTurnService.getCardsFromPlayerDeck(gameDTO.getBoard().getPlayerQueue(), gameDTO.getTurnInformation().getActivePlayer());
            endOfTurnService.getCardsFromInfectionDeck(gameDTO.getBoard().getNumberInfectionCard(), gameDTO.getBoard().getInfectionDeck(), gameDTO.getBoard().getInfectionDiscardDeck());
            endOfTurnService.setNewTurnAndPlayer(gameDTO.getBoard().getPlayers(),gameDTO.getTurnInformation());
        }

        gamePersistence.insertOrUpdateGame(gameDTO);

        return gameDTO.getTurnInformation();
    }

    @Override
    public Action validateActionFormat(TurnExecuteDTO turnExecuteDTO, Action action, HashMap<String, String> additionalFields) throws GameExecutionException {

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

    @Override
    public void ifEndOfGameThrowExcepction(UUID gameId) throws EndOfGameException, GameExecutionException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);
        if (isNull(gameDTO)) {
            throw new GameExecutionException(GAME_NOT_FOUND);
        }

        if(endOfGameService.allVirusHadBeenEradicated(gameDTO.getBoard().getVirusList())
                && endOfGameService.allCitiesWithoutBoxes(gameDTO.getBoard().getBoardCities()))
            throw new EndOfGameException(END_OF_GAME_VICTORY, true);

        VirusType virusOverLimit = endOfGameService.returnVirusIfOverPassTheMaximalNumberOrNull(gameDTO.getBoard().getBoardCities());
        if(!isNull(virusOverLimit)) {
            throw new EndOfGameException(END_OF_GAME_MAX_VIRUS_SAME_TYPE + virusOverLimit.name());
        }

    }

}

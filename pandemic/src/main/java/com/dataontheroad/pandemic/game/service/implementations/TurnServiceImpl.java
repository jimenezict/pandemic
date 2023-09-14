package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyCharterAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.interfaces.ITurnService;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.dataontheroad.pandemic.actions.ActionsType.FLYCHARTER;
import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static com.dataontheroad.pandemic.game.ActionServiceHelper.getListOfActions;
import static com.dataontheroad.pandemic.game.TurnServiceHelper.*;
import static java.util.Objects.isNull;

@Service
public class TurnServiceImpl implements ITurnService {

    @Autowired
    GamePersistenceOnHashMap gamePersistence;

    @Override
    public TurnResponseDTO getTurnServiceInformation(UUID gameId) {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);
        TurnResponseDTO turnResponseDTO = new TurnResponseDTO();

        if (!isNull(gameDTO)) {
            List<Action> actionList = getListOfActions(gameDTO.getTurnInformation().getActivePlayer(),
                    gameDTO.getBoard().getVirusList(),
                    getCitiesWithResearchCenter(gameDTO),
                    getOtherPlayersOnTheCity(gameDTO));
            turnResponseDTO.setTurnInformation(gameDTO.getTurnInformation(), actionList);
            return turnResponseDTO;
        } else {
            return null;
        }
    }

    @Override
    public TurnInformation executeAction(UUID gameId, Action action) throws ActionException, GameExecutionException, EndOfGameException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);

        if (isNull(gameDTO)) {
            throw new GameExecutionException(GAME_NOT_FOUND);
        }

        action.execute();
        if (!gameDTO.getTurnInformation().canDoNextActionAndReduceMissingTurns()) {
            if (playerGetNewCardsIfIsNotEpidemic(gameDTO.getBoard().getPlayerQueue(), gameDTO.getTurnInformation().getActivePlayer())) {
                playerGetNewCardsIfIsNotEpidemic(gameDTO.getBoard().getPlayerQueue(), gameDTO.getTurnInformation().getActivePlayer());
            }
            //infectionPhase(gameDTO.getBoard().getPlayerQueue(), gameDTO.getTurnInformation().getActivePlayer());

            Player player = getNextActivePlayer(gameDTO.getBoard().getPlayers(),
                    gameDTO.getTurnInformation().getActivePlayer());
            gameDTO.getTurnInformation().setNewTurn(player);
            gamePersistence.insertOrUpdateGame(gameDTO);
        }

        return gameDTO.getTurnInformation();
    }

    @Override
    public Action getSelectedAction(UUID gameId, int actionPosition) throws GameExecutionException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);

        if (isNull(gameDTO)) {
            throw new GameExecutionException(GAME_NOT_FOUND);
        }

        return getListOfActions(gameDTO.getTurnInformation().getActivePlayer(),
                gameDTO.getBoard().getVirusList(),
                getCitiesWithResearchCenter(gameDTO),
                getOtherPlayersOnTheCity(gameDTO)).get(actionPosition);
    }

    @Override
    public Action actionFormatValidation(UUID gameId, Action action, HashMap<String, String> additionalFields) throws GameExecutionException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);
        if (isNull(gameDTO)) {
            throw new GameExecutionException(GAME_NOT_FOUND);
        }
        if(FLYCHARTER.equals(action.getActionsType())) {
            if(!isNull(additionalFields) && additionalFields.containsKey(ADDITIONAL_FIELD_DESTINATION)) {
                City city = gameDTO.getBoard().getCityFromBoardList(new City(additionalFields.get(ADDITIONAL_FIELD_DESTINATION), null));
                if(isNull(city)) {
                    throw new GameExecutionException(TURN_WRONG_FLYCHARTER_INVALID_DESTINATION_CITY);
                }
                ((FlyCharterAction) action).setDestination(city);
            } else {
                throw new GameExecutionException(TURN_WRONG_FLYCHARTER_DESTINATION_FIELD);
            }
        }
        return action;
    }


}

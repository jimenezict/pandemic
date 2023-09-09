package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.interfaces.ITurnService;
import com.dataontheroad.pandemic.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.GAME_NOT_FOUND;
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
    public TurnInformation executeAction(UUID gameId, int actionPosition) throws ActionException, GameExecutionException, EndOfGameException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);

        if (isNull(gameDTO)) {
            throw new GameExecutionException(GAME_NOT_FOUND);
        }

        List<Action> actionList = getListOfActions(gameDTO.getTurnInformation().getActivePlayer(),
                gameDTO.getBoard().getVirusList(),
                getCitiesWithResearchCenter(gameDTO),
                getOtherPlayersOnTheCity(gameDTO));
        actionList.get(actionPosition).execute();
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


}

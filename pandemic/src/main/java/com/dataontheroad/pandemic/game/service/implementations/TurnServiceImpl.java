package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.interfaces.ITurnService;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.game.ActionService.getListOfActions;
import static java.util.Objects.isNull;

@Service
public class TurnServiceImpl implements ITurnService {

    @Autowired
    GamePersistenceOnHashMap gamePersistence;

    @Override
    public TurnResponseDTO getTurnServiceInformation(UUID gameId) {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);
        TurnResponseDTO turnResponseDTO = new TurnResponseDTO();

        if(!isNull(gameDTO)) {
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
    public TurnInformation executeAction(UUID gameId, int actionPosition) throws ActionException {
        GameDTO gameDTO = gamePersistence.getGameById(gameId);

        if(!isNull(gameDTO)) {
            List<Action> actionList = getListOfActions(gameDTO.getTurnInformation().getActivePlayer(),
                    gameDTO.getBoard().getVirusList(),
                    getCitiesWithResearchCenter(gameDTO),
                    getOtherPlayersOnTheCity(gameDTO));
            actionList.get(actionPosition).execute();
            if(!gameDTO.getTurnInformation().canDoNextActionAndReduceMissingTurns()) {
                Player player = getNextActivePlayer(gameDTO.getBoard().getPlayers(),
                        gameDTO.getTurnInformation().getActivePlayer());
                gameDTO.getTurnInformation().setNewTurn(player);
                gamePersistence.insertOrUpdateGame(gameDTO);
            }
        }
        return gameDTO.getTurnInformation();
    }

    public static Player getNextActivePlayer(List<Player> playerList, Player activePlayer) {
        int pos = playerList.indexOf(activePlayer);
        return playerList.get((pos + 1) % playerList.size());
    }

    public static List<Player> getOtherPlayersOnTheCity(GameDTO gameDTO) {
        return gameDTO.getBoard().getPlayers()
                .stream()
                .filter(player -> !player.getName().equals(gameDTO.getTurnInformation().getActivePlayer().getName()))
                .filter(player -> player.getCity().equals(gameDTO.getTurnInformation().getActivePlayer().getCity()))
                .collect(Collectors.toList());
    }

    public static List<City> getCitiesWithResearchCenter(GameDTO gameDTO) {
        return gameDTO.getBoard().getBoardCities().stream().filter(City::getHasCenter).collect(Collectors.toList());
    }
}

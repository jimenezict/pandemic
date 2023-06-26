package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.service.implementations.ITurnService;
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
                    gameDTO.getBoard().getBoardCities().stream().filter(city -> city.getHasCenter()).collect(Collectors.toList()),
                    gameDTO.getBoard().getPlayers().stream().filter(player -> player.getCity().equals(gameDTO.getTurnInformation().getActivePlayer())).collect(Collectors.toList()));
            turnResponseDTO.setTurnInformation(gameDTO.getTurnInformation(), actionList);
        } else {
            turnResponseDTO = null;
        }
        return turnResponseDTO;
    }
}

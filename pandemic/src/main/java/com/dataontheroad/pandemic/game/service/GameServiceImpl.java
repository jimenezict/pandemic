package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dataontheroad.pandemic.game.service.ConvertGamesDTO.convertGameDTO;
import static com.dataontheroad.pandemic.model.board.BoardCreationHelper.addEpidemicCards;
import static java.util.Objects.isNull;

@Service
public class GameServiceImpl implements IGameService {

    @Autowired
    GamePersistenceOnHashMap gamePersistence;

    @Override
    public UUID createGame(int numEpidemic, int numPlayers) throws GameExecutionException {
        GameDTO gameDTO = null;
        try {
            gameDTO = new GameDTO(numPlayers);
            gameDTO.setUpdateDateTime(LocalDateTime.now());
        } catch (Exception ex) {
            throw new GameExecutionException(ex.getMessage());
        }
        addEpidemicCards(gameDTO.getBoard(), numEpidemic);
        gamePersistence.insertOrUpdateGame(gameDTO);
        return gameDTO.getUuid();
    }

    @Override
    public GameResponseDTO getGameById(UUID uuid) {
        GameDTO gameDTO = gamePersistence.getGameById(uuid);
        return isNull(gameDTO) ? null : convertGameDTO(gamePersistence.getGameById(uuid));
    }
}

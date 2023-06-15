package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.GameResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dataontheroad.pandemic.game.service.ConvertGamesDTO.convertGameDTO;

public class GameServiceImpl implements IGameService {

    @Autowired
    GamePersistenceOnHashMap gamePersistence;

    @Override
    public UUID createGame(int numEpidemic, int numPlayers) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setUpdateDateTime(LocalDateTime.now());
        gamePersistence.insertOrUpdateGame(gameDTO);
        return gameDTO.getUuid();
    }

    @Override
    public GameResponseDTO getGameById(UUID uuid) {
        return convertGameDTO(gamePersistence.getGameById(uuid));
    }
}

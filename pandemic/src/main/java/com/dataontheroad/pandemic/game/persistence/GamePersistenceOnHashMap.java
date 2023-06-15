package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameHashMapSingleton;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class GamePersistenceOnHashMap implements IGamePersistence {
    @Override
    public void insertOrUpdateGame(GameDTO gameDTO) {
        GameHashMapSingleton.getInstance().put(gameDTO.getUuid(), gameDTO);
    }

    @Override
    public GameDTO getGameById(UUID uuid) {
        return GameHashMapSingleton.getInstance().get(uuid);
    }
}

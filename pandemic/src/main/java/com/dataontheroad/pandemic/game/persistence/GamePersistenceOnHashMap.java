package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameHashMapSingleton;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Qualifier("gamePersistenceOnHashMap")
public class GamePersistenceOnHashMap extends GamePersistenceAbstractClass {
    @Override
    public void insertOrUpdateGame(GameDTO gameDTO) {
        GameHashMapSingleton.getInstance().put(gameDTO.getUuid(), gameDTO);
    }

    @Override
    public GameDTO getGameById(UUID uuid) {
        return GameHashMapSingleton.getInstance().get(uuid);
    }
}

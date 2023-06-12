package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.Game;
import com.dataontheroad.pandemic.game.persistence.model.GameHashMapSingleton;

import java.util.UUID;

public class GamePersistenceOnHashMap implements IGamePersistence {
    @Override
    public void insertOrUpdateGame(Game game) {
        GameHashMapSingleton.getInstance().put(game.getUuid(), game);
    }

    @Override
    public Game getGameById(UUID uuid) {
        return GameHashMapSingleton.getInstance().get(uuid);
    }
}

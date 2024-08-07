package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;

import java.util.UUID;

public abstract class GamePersistenceAbstractClass implements IGamePersistence {
    @Override
    public void insertOrUpdateGame(GameDTO gameDTO) {

    }

    @Override
    public GameDTO getGameById(UUID uuid) {
        return null;
    }
}

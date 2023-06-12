package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.Game;

import java.util.UUID;

public interface IGamePersistence {

    void insertOrUpdateGame(Game game);

    Game getGameById(UUID uuid);

}

package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.persistence.IGamePersistence;
import com.dataontheroad.pandemic.game.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class IGameServiceImpl implements IGameService {

    @Autowired
    IGamePersistence gamePersistence;

    @Override
    public UUID createGame(int numEpidemic, int numPlayers) {
        Game game = new Game();
        gamePersistence.insertOrUpdateGame(game);
        return game.getUuid();
    }

    @Override
    public Game getGameById(UUID uuid) {
        return gamePersistence.getGameById(uuid);
    }
}

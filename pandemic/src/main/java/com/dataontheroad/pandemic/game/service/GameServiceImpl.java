package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.Game;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

public class GameServiceImpl implements IGameService {

    @Autowired
    GamePersistenceOnHashMap gamePersistence;

    @Override
    public UUID createGame(int numEpidemic, int numPlayers) {
        Game game = new Game();
        game.setUpdateDateTime(LocalDateTime.now());
        gamePersistence.insertOrUpdateGame(game);
        return game.getUuid();
    }

    @Override
    public Game getGameById(UUID uuid) {
        return gamePersistence.getGameById(uuid);
    }
}

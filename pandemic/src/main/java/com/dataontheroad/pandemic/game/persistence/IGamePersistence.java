package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.Game;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IGamePersistence {

    void insertOrUpdateGame(Game game);

    Game getGameById(UUID uuid);

}

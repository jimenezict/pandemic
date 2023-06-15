package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IGamePersistence {

    void insertOrUpdateGame(GameDTO gameDTO);

    GameDTO getGameById(UUID uuid);

}

package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;

import java.util.UUID;


public interface IGamePersistence {

    void insertOrUpdateGame(GameDTO gameDTO);

    GameDTO getGameById(UUID uuid);

}

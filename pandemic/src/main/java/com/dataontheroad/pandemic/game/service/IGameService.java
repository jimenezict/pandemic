package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.persistence.model.Game;

import java.util.UUID;

public interface IGameService {

    UUID createGame(int numEpidemic, int numPlayers);

    Game getGameById(UUID uuid);
}

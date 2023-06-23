package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;

import java.util.UUID;

public interface IGameService {

    UUID createGame(int numEpidemic, int numPlayers) throws Exception, GameExecutionException;

    GameResponseDTO getGameById(UUID uuid);
}

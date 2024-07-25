package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;

import java.util.UUID;

public interface IGameService {

    UUID createGame(int numEpidemic, int numPlayers) throws GameExecutionException;

    GameResponseDTO getGameById(UUID uuid);

}

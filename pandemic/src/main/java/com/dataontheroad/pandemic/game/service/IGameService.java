package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.GameResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;

import java.util.UUID;

public interface IGameService {

    UUID createGame(int numEpidemic, int numPlayers) throws Exception;

    GameResponseDTO getGameById(UUID uuid);
}

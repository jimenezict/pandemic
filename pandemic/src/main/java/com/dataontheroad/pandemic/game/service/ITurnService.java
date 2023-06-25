package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;

import java.util.UUID;

public interface ITurnService {
    TurnResponseDTO getTurnServiceInformation(UUID gameId);
}

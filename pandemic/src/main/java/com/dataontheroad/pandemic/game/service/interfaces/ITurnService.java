package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;

import java.util.UUID;

public interface ITurnService {
    TurnResponseDTO getTurnServiceInformation(UUID gameId);

    void executeAction(UUID uuid, int actionPosition) throws ActionException;
}

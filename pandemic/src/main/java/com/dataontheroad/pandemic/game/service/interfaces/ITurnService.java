package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;

import java.util.UUID;

public interface ITurnService {
    TurnResponseDTO getTurnServiceInformation(UUID gameId);

    TurnInformation executeAction(UUID uuid, int actionPosition) throws ActionException;
}

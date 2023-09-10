package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;

import java.util.UUID;

public interface ITurnService {
    TurnResponseDTO getTurnServiceInformation(UUID gameId);

    TurnInformation executeAction(UUID gameId, Action actionPosition) throws ActionException, GameExecutionException, EndOfGameException;

    Action getSelectedAction(UUID uuid, int actionPosition) throws GameExecutionException;
}

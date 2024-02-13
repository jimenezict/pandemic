package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.game.api.model.commons.SuccessResponse;

import java.util.UUID;

public class EndOfGameResponse extends SuccessResponse {

    public EndOfGameResponse(String endpoint, UUID gameID, String message) {
        super(endpoint, gameID, message);
    }
}

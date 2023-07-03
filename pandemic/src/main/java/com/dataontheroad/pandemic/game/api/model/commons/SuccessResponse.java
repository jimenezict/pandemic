package com.dataontheroad.pandemic.game.api.model.commons;

import java.util.UUID;

public class SuccessResponse {

    private final String endpoint;
    private final UUID gameID;
    private final String message;

    public SuccessResponse(String endpoint, UUID gameID, String message) {
        this.endpoint = endpoint;
        this.gameID = gameID;
        this.message = message;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public UUID getGameID() {
        return gameID;
    }

    public String getMessage() {
        return message;
    }
}

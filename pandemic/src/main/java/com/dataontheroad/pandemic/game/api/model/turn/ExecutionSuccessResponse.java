package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.game.api.model.commons.SuccessResponse;

import java.util.UUID;

public class ExecutionSuccessResponse extends SuccessResponse {

    private final String playerName;

    private final String playerLocation;

    public ExecutionSuccessResponse(String endpoint, UUID gameID, String message, String playerName, String playerLocation) {
        super(endpoint, gameID, message);
        this.playerName = playerName;
        this.playerLocation = playerLocation;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerLocation() {
        return playerLocation;
    }
}

package com.dataontheroad.pandemic.game.api.model.turn;

import java.util.HashMap;
import java.util.UUID;

public class TurnRequestDTO {

    private final UUID uuid;
    private final String playerName;
    private final int actionPosition;

    private final HashMap<String, String> additionalFields;

    public TurnRequestDTO(UUID uuid, String playerName, int actionPosition, HashMap<String, String> additionalFields) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.actionPosition = actionPosition;
        this.additionalFields = additionalFields;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getActionPosition() {
        return actionPosition;
    }

    public HashMap<String, String> getAdditionalFields() {
        return additionalFields;
    }
}

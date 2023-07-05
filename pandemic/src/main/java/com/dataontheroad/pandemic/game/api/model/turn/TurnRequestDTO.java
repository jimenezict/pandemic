package com.dataontheroad.pandemic.game.api.model.turn;

import java.util.UUID;

public class TurnRequestDTO {

    private final UUID uuid;
    private final String playerName;
    private final int actionPosition;

    public TurnRequestDTO(UUID uuid, String playerName, int actionPosition) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.actionPosition = actionPosition;
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
}

package com.dataontheroad.pandemic.game.api.model;

import java.util.UUID;

public class GameResponseDTO {

    private final UUID gameId;

    public GameResponseDTO(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getUuid() {
        return gameId;
    }
}

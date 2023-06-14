package com.dataontheroad.pandemic.game.persistence.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class Game {

    private final UUID uuid;

    private final LocalDateTime insertDateTime;

    private LocalDateTime updateDateTime;

    public Game() {
        this.uuid = randomUUID();
        insertDateTime = LocalDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getLocalDate() {
        return insertDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}

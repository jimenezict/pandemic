package com.dataontheroad.pandemic.game.persistence.model;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class Game {

    private final UUID uuid;

    public Game() {
        this.uuid = randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }
}

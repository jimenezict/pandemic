package com.dataontheroad.pandemic.game.persistence.model;

import java.util.HashMap;
import java.util.UUID;

import static java.util.Objects.isNull;

public class GameHashMapSingleton {

    private static HashMap<UUID, Game> gameHashMap;

    public static HashMap<UUID, Game> getInstance() {
        if(isNull(gameHashMap)) {
            gameHashMap = new HashMap<>();
        }
        return gameHashMap;
    }
}

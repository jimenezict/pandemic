package com.dataontheroad.pandemic.game.persistence.model;

import java.util.HashMap;
import java.util.UUID;

import static java.util.Objects.isNull;

public class GameHashMapSingleton {

    private static HashMap<UUID, GameDTO> gameHashMap;

    public static HashMap<UUID, GameDTO> getInstance() {
        if(isNull(gameHashMap)) {
            gameHashMap = new HashMap<>();
        }
        return gameHashMap;
    }
}

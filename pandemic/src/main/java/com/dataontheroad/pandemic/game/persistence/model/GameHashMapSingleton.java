package com.dataontheroad.pandemic.game.persistence.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;

public class GameHashMapSingleton {

    private static HashMap<UUID, GameDTO> gameHashMap;

    public static Map<UUID, GameDTO> getInstance() {
        if(isNull(gameHashMap)) {
            gameHashMap = new HashMap<>();
        }
        return gameHashMap;
    }
}

package com.dataontheroad.pandemic.game.api.model.game;

import java.util.List;

public class GameResponsePlayer {

    private final List<GameResponseCard> listCard;

    private final GameResponseCity location;

    private final String name;

    public GameResponsePlayer(List<GameResponseCard> listCard, GameResponseCity location, String name) {
        this.listCard = listCard;
        this.location = location;
        this.name = name;
    }

    public List<GameResponseCard> getListCard() {
        return listCard;
    }

    public GameResponseCity getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}

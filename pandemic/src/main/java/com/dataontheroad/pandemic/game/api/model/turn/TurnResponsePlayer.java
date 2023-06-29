package com.dataontheroad.pandemic.game.api.model.turn;

import java.util.List;

public class TurnResponsePlayer {

    private final List<String> listCard;

    private final TurnResponseCity location;

    private final String name;

    public TurnResponsePlayer(List<String> listCard, TurnResponseCity location, String name) {
        this.listCard = listCard;
        this.location = location;
        this.name = name;
    }

    public List<String> getListCard() {
        return listCard;
    }

    public TurnResponseCity getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}

package com.dataontheroad.pandemic.game.api.model;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;

import java.util.List;

public class GameResponsePlayer {

    private final List<BaseCard> listCard;

    private final City location;

    private final String name;

    public GameResponsePlayer(List<BaseCard> listCard, City location, String name) {
        this.listCard = listCard;
        this.location = location;
        this.name = name;
    }

    public List<BaseCard> getListCard() {
        return listCard;
    }

    public City getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}

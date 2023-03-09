package com.dataontheroad.pandemic.model;

import java.util.List;

public class Player {
    private List<Card> listCard;
    private City city;

    public List<Card> getListCard() {
        return listCard;
    }

    public void setListCard(List<Card> listCard) {
        this.listCard = listCard;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}

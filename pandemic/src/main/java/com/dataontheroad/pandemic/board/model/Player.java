package com.dataontheroad.pandemic.board.model;

import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<CityCard> listCard;
    private City city;

    public Player() {
        listCard = new ArrayList<>();
    }

    public Player(City city) {
        listCard = new ArrayList<>();
        this.city = city;
    }

    public List<CityCard> getListCard() {
        return listCard;
    }

    public void setListCard(List<CityCard> listCard) {
        this.listCard = listCard;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}

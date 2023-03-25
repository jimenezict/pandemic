package com.dataontheroad.pandemic.board.model;

import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.enums.CardType;
import com.dataontheroad.pandemic.board.model.enums.VirusType;

public class Card {
    private City city;
    private VirusType virus;
    private CardType cardType;
    private Card(City city, VirusType virus, CardType cardType) {
        this.city = city;
        this.cardType = cardType;
        this.virus = virus;
    }

    public static Card createCityCard(City city) {
        return new Card(city, city.getVirus(), CardType.CITY);
    }

    public static Card createInfectionCard(City city) {
        return new Card(city, city.getVirus(), CardType.INFECTION);
    }

    public City getCity() {
        return city;
    }

    public VirusType getVirus() {
        return virus;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public boolean equals(Object obj) {
        Card card = (Card) obj;
        return card.getCity().getName().equals(city.getName());
    }
}

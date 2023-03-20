package com.dataontheroad.pandemic.model;

import static com.dataontheroad.pandemic.model.CardType.CITY;
import static com.dataontheroad.pandemic.model.CardType.INFECTION;

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
        return new Card(city, city.getVirus(), CITY);
    }

    public static Card createInfectionCard(City city, VirusType virus) {
        return new Card(city, city.getVirus(), INFECTION);
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

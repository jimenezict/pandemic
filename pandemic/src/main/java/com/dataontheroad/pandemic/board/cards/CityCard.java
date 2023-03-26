package com.dataontheroad.pandemic.board.cards;

import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.enums.VirusType;

public class CityCard extends BaseCard{
    private final City city;
    private final VirusType virus;

    private CityCard(City city, CardType cardType) {
        super(cardType);
        this.city = city;
        this.virus = city.getVirus();
    }

    public static CityCard createCityCard(City city) {
        return new CityCard(city, CardType.CITY);
    }

    public static CityCard createInfectionCard(City city) {
        return new CityCard(city, CardType.INFECTION);
    }

    public City getCity() {
        return city;
    }

    public VirusType getVirus() {
        return virus;
    }

    @Override
    public boolean equals(Object obj) {
        CityCard card = (CityCard) obj;
        return card.getCity().getName().equals(city.getName());
    }
}

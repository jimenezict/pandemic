package com.dataontheroad.pandemic.board.cards.model;

import com.dataontheroad.pandemic.board.cards.CardTypeEnum;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.enums.VirusType;

public class CityCard extends BaseCard{
    private final City city;
    private final VirusType virus;

    private CityCard(City city, CardTypeEnum cardTypeEnum) {
        super(cardTypeEnum);
        this.city = city;
        this.virus = city.getVirus();
    }

    public static CityCard createCityCard(City city) {
        return new CityCard(city, CardTypeEnum.CITY);
    }

    public static CityCard createInfectionCard(City city) {
        return new CityCard(city, CardTypeEnum.INFECTION);
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

package com.dataontheroad.pandemic.model.cards.model;

import com.dataontheroad.pandemic.model.cards.CardTypeEnum;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.io.Serializable;

public class CityCard extends BaseCard implements Serializable {

    private static final long serialVersionUID = 1L;
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
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        CityCard card = (CityCard) obj;
        return card.getCity().getName().equals(city.getName());
    }

    @Override
    public int hashCode() {
        return this.city.getName().hashCode();
    }

    @Override
    public String toString() {
        return "Type: " + cardTypeEnum.name() + " City: " + city.getName() + " Virus Type: " + virus.name();
    }
}

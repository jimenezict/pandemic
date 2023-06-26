package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.VirusType;

public class TurnResponseCity {

    private final boolean hasCenter;
    private final VirusType virus;
    private final String name;

    public TurnResponseCity(City city) {
        hasCenter = city.getHasCenter();
        virus = city.getVirus();
        name = city.getName();
    }

    public boolean isHasCenter() {
        return hasCenter;
    }

    public VirusType getVirus() {
        return virus;
    }

    public String getName() {
        return name;
    }
}

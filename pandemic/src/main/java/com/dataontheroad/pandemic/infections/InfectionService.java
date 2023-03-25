package com.dataontheroad.pandemic.infections;

import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.enums.VirusType;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class InfectionService {

    public Boolean infectCityIfPossible(City city) {
        if(city.getVirusBoxes().size() == 3) {
            return FALSE;
        }
        city.getVirusBoxes().add(city.getVirus());
        return TRUE;
    }

    public Boolean infectCityIfPossible(City city, VirusType virusType) {
        if(city.getVirusBoxes().size() == 3) {
            return FALSE;
        }
        city.getVirusBoxes().add(virusType);
        return TRUE;
    }

    public void setInitialInfections(City city, int times) {
        for(int i=0; i< times; i++) {
            city.getVirusBoxes().add(city.getVirus());
        }
    }

    public void cityOutbreaks(City city) {

    }
}

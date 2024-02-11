package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.List;

public interface IEndOfGameService {
    boolean allVirusHadBeenEradicated(List<Virus> virusList);

    VirusType returnVirusIfOverPassTheMaximalNumberOrNull(List<City> listOfCities);

    boolean allCitiesWithoutBoxes(List<City> listOfCities);
}

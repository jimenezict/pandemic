package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.List;

public interface PreventPropagationOfVirusInterface {

    List<City> getCitiesWherePreventsToPropagate(List<VirusType> listOfVirus);

}

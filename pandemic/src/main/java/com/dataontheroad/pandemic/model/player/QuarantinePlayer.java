package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class QuarantinePlayer extends Player implements PreventPropagationOfVirusInterface {

    public QuarantinePlayer() {
        super();
        color = QUARANTINE_COLOR;
        name = QUARANTINE_NAME;
        description = QUARANTINE_DESCRIPTION;
    }

    @Override
    public List<City> getCitiesWherePreventsToPropagate(List<VirusType> listOfVirus) {
        List<City> cityList = new ArrayList<>();
        cityList.addAll(this.getCity().getNodeCityConnection());
        cityList.add(this.getCity());
        return cityList;
    }

}

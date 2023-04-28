package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.model.city.City;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class QuarantinePlayer extends Player {

    public QuarantinePlayer() {
        super();
        color = QUARANTINE_COLOR;
        name = QUARANTINE_NAME;
        description = QUARANTINE_DESCRIPTION;
    }

    public List<City> getCitiesWherePreventsToPropagate() {
        List<City> cityList = new ArrayList<>();
        cityList.addAll(this.getCity().getNodeCityConnection());
        cityList.add(this.getCity());
        return cityList;
    }

}

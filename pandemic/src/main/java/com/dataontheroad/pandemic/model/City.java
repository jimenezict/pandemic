package com.dataontheroad.pandemic.model;

import java.util.ArrayList;
import java.util.List;

public class City {

    private Boolean hasCenter = false;
    private final VirusType virus;
    private final String name;
    private final List<City> nodeCityConnection;

    private List<VirusType> virusBoxes;
    public City(String name, VirusType virus, List<City> nodeCityConnection) {
        this.name = name;
        this.virus = virus;
        this.nodeCityConnection = nodeCityConnection;
        virusBoxes = new ArrayList<>();
    }

    public Boolean getHasCenter() {
        return hasCenter;
    }

    public void setHasCenter(Boolean hasCenter) {
        this.hasCenter = hasCenter;
    }

    public String getName() {
        return name;
    }

    public VirusType getVirus() {
        return virus;
    }

    public List<City> getNodeCityConnection() {
        return nodeCityConnection;
    }

    public List<VirusType> getVirusBoxes() {
        return virusBoxes;
    }

    public void setVirusBoxes(List<VirusType> virusBoxes) {
        this.virusBoxes = virusBoxes;
    }
}

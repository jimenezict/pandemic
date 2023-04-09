package com.dataontheroad.pandemic.board.city;

import com.dataontheroad.pandemic.board.virus.VirusType;

import java.util.ArrayList;
import java.util.List;

public class City {

    private boolean hasCenter = Boolean.FALSE;
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

    public City(String name, VirusType virus) {
        this.name = name;
        this.virus = virus;
        this.nodeCityConnection = new ArrayList<>();
        virusBoxes = new ArrayList<>();
    }

    public boolean getHasCenter() {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        City city = (City) obj;
        return city.getName().equals(name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}

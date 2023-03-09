package com.dataontheroad.pandemic.model;

public class City {

    private Boolean hasCenter = false;
    private final Virus virus;
    private final String name;

    public City(String name, Virus virus) {
        this.name = name;
        this.virus = virus;
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

    public Virus getVirus() {
        return virus;
    }
}

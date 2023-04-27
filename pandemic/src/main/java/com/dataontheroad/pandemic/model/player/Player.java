package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.*;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<BaseCard> listCard;
    private City city;
    protected String color;
    protected String name;
    protected String description;
    protected int numOfCardsForDiscoveringCure;

    public Player() {
        listCard = new ArrayList<>();
        numOfCardsForDiscoveringCure = 5;
    }

    public Player(City city) {
        listCard = new ArrayList<>();
        numOfCardsForDiscoveringCure = 5;
        this.city = city;
    }

    public List<BaseCard> getListCard() {
        return listCard;
    }

    public void setListCard(List<BaseCard> listCard) {
        this.listCard = listCard;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
    public int getNumOfCardsForDiscoveringCure() {
        return numOfCardsForDiscoveringCure;
    }

    public BuildResearchCenterDefaultService getBuildResearchCenter() {
        return BuildResearchCenterDefaultService.getInstance();
    }

    public DiscoverCureDefaultService getDiscoverCure() {
        return DiscoverCureDefaultService.getInstance();
    }

    public DriveFerryDefaultService getDriveFerry() {
        return DriveFerryDefaultService.getInstance();
    }

    public FlyCharterDefaultService getFlyCharter() {
        return FlyCharterDefaultService.getInstance();
    }

    public FlyDirectCityDefaultService getFlyDirectCity() {
        return FlyDirectCityDefaultService.getInstance();
    }

    public FlyShuttleDefaultService getFlyShuttle() {
        return FlyShuttleDefaultService.getInstance();
    }

    public ShareKnowledgeDefaultService getShareKnowledge() {
        return ShareKnowledgeDefaultService.getInstance();
    }

    public TreatDiseaseDefaultService getTreatDisease() { return TreatDiseaseDefaultService.getInstance();}
}

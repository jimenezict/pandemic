package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.*;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayerServices, Serializable {
    private static final long serialVersionUID = 1L;
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
        this();
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

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public BuildResearchCenterDefaultService getBuildResearchCenter() {
        return BuildResearchCenterDefaultService.getInstance();
    }
    @Override
    public DiscoverCureDefaultService getDiscoverCure() {
        return DiscoverCureDefaultService.getInstance();
    }

    @Override
    public DriveFerryDefaultService getDriveFerry() {
        return DriveFerryDefaultService.getInstance();
    }

    @Override
    public FlyCharterDefaultService getFlyCharter() {
        return FlyCharterDefaultService.getInstance();
    }

    @Override
    public FlyDirectCityDefaultService getFlyDirectCity() {
        return FlyDirectCityDefaultService.getInstance();
    }
    @Override
    public FlyShuttleDefaultService getFlyShuttle() {
        return FlyShuttleDefaultService.getInstance();
    }

    @Override
    public ShareKnowledgeDefaultService getShareKnowledge() {
        return ShareKnowledgeDefaultService.getInstance();
    }

    @Override
    public TreatDiseaseDefaultService getTreatDisease() {
        return TreatDiseaseDefaultService.getInstance();
    }
}

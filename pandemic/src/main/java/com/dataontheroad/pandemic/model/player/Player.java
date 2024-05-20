package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.*;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;

import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayerServices {
    private List<BaseCard> listCard;
    private City city;
    protected String color;
    protected String name;
    protected String description;
    protected int numOfCardsForDiscoveringCure;
    protected FlyDirectCityDefaultService flyDirectCityDefaultService;
    protected FlyShuttleDefaultService flyShuttleDefaultService;
    protected ShareKnowledgeDefaultService shareKnowledgeDefaultService;
    protected TreatDiseaseDefaultService treatDiseaseDefaultService;


    public Player() {
        listCard = new ArrayList<>();
        numOfCardsForDiscoveringCure = 5;
        flyDirectCityDefaultService = new FlyDirectCityDefaultService();
        flyShuttleDefaultService = new FlyShuttleDefaultService();
        shareKnowledgeDefaultService = new ShareKnowledgeDefaultService();
        treatDiseaseDefaultService = new TreatDiseaseDefaultService();
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
        return flyDirectCityDefaultService;
    }
    @Override
    public FlyShuttleDefaultService getFlyShuttle() {
        return flyShuttleDefaultService;
    }

    @Override
    public ShareKnowledgeDefaultService getShareKnowledge() {
        return shareKnowledgeDefaultService;
    }

    @Override
    public TreatDiseaseDefaultService getTreatDisease() {
        return treatDiseaseDefaultService;
    }
}

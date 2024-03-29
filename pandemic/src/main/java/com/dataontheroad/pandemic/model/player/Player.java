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

    protected BuildResearchCenterDefaultService buildResearchCenterDefaultService;
    protected DiscoverCureDefaultService discoverCureDefaultService;
    protected DriveFerryDefaultService driveFerryDefaultService;
    protected FlyCharterDefaultService flyCharterDefaultService;
    protected FlyDirectCityDefaultService flyDirectCityDefaultService;
    protected FlyShuttleDefaultService flyShuttleDefaultService;
    protected ShareKnowledgeDefaultService shareKnowledgeDefaultService;
    protected TreatDiseaseDefaultService treatDiseaseDefaultService;


    public Player() {
        listCard = new ArrayList<>();
        numOfCardsForDiscoveringCure = 5;
        buildResearchCenterDefaultService = new BuildResearchCenterDefaultService();
        discoverCureDefaultService = new DiscoverCureDefaultService();
        driveFerryDefaultService = new DriveFerryDefaultService();
        flyCharterDefaultService = new FlyCharterDefaultService();
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
        return buildResearchCenterDefaultService;
    }
    @Override
    public DiscoverCureDefaultService getDiscoverCure() {
        return discoverCureDefaultService;
    }

    @Override
    public DriveFerryDefaultService getDriveFerry() {
        return driveFerryDefaultService;
    }

    @Override
    public FlyCharterDefaultService getFlyCharter() {
        return flyCharterDefaultService;
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

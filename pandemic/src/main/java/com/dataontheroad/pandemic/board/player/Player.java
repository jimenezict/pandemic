package com.dataontheroad.pandemic.board.player;

import com.dataontheroad.pandemic.actions.default_services.*;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<CityCard> listCard;
    private City city;
    private BuildResearchCenterDefaultService buildResearchCenter;
    private DiscoverCureDefaultService discoverCure;
    private DriveFerryDefaultService driveFerry;
    private FlyCharterDefaultService flyCharter;
    private FlyDirectCityDefaultService flyDirectCity;
    private FlyShuttleDefaultService flyShuttle;
    private ShareKnowledgeDefaultService shareKnowledge;

    public Player() {
        listCard = new ArrayList<>();
    }

    public Player(City city) {
        listCard = new ArrayList<>();
        this.city = city;
        buildResearchCenter = new BuildResearchCenterDefaultService();
        discoverCure = new DiscoverCureDefaultService();
        driveFerry = new DriveFerryDefaultService();
        flyCharter = new FlyCharterDefaultService();
        flyDirectCity = new FlyDirectCityDefaultService();
        flyShuttle = new FlyShuttleDefaultService();
        shareKnowledge = new ShareKnowledgeDefaultService();
    }

    public List<CityCard> getListCard() {
        return listCard;
    }

    public void setListCard(List<CityCard> listCard) {
        this.listCard = listCard;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public BuildResearchCenterDefaultService getBuildResearchCenter() {
        return buildResearchCenter;
    }

    public DiscoverCureDefaultService getDiscoverCure() {
        return discoverCure;
    }

    public DriveFerryDefaultService getDriveFerry() {
        return driveFerry;
    }

    public FlyCharterDefaultService getFlyCharter() {
        return flyCharter;
    }

    public FlyDirectCityDefaultService getFlyDirectCity() {
        return flyDirectCity;
    }

    public FlyShuttleDefaultService getFlyShuttle() {
        return flyShuttle;
    }

    public ShareKnowledgeDefaultService getShareKnowledge() {
        return shareKnowledge;
    }
}

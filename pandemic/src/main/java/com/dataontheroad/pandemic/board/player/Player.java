package com.dataontheroad.pandemic.board.player;

import com.dataontheroad.pandemic.actions.default_services.*;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<CityCard> listCard;
    private City city;

    public Player() {
        listCard = new ArrayList<>();
    }

    public Player(City city) {
        listCard = new ArrayList<>();
        this.city = city;
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
        return new BuildResearchCenterDefaultService();
    }

    public DiscoverCureDefaultService getDiscoverCure() {
        return new DiscoverCureDefaultService();
    }

    public DriveFerryDefaultService getDriveFerry() {
        return new DriveFerryDefaultService();
    }

    public FlyCharterDefaultService getFlyCharter() {
        return new FlyCharterDefaultService();
    }

    public FlyDirectCityDefaultService getFlyDirectCity() {
        return new FlyDirectCityDefaultService();
    }

    public FlyShuttleDefaultService getFlyShuttle() {
        return new FlyShuttleDefaultService();
    }

    public ShareKnowledgeDefaultService getShareKnowledge() {
        return new ShareKnowledgeDefaultService();
    }
}

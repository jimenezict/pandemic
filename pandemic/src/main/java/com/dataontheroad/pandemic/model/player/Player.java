package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.*;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<BaseCard> listCard;
    private City city;

    public Player() {
        listCard = new ArrayList<>();
    }

    public Player(City city) {
        listCard = new ArrayList<>();
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
}

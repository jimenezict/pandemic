package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyShuttleAction;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.SHUTTLEFLIGHT_ERROR_DESTINY_NO_RESEARCH_STATION;
import static com.dataontheroad.pandemic.constants.LiteralsAction.SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION;
import static java.util.Objects.isNull;

public class FlyShuttleDefaultService {

    private static FlyShuttleDefaultService flyShuttleDefaultService;

    private FlyShuttleDefaultService(){}

    public static FlyShuttleDefaultService getInstance() {
        if(isNull(flyShuttleDefaultService)) {
            flyShuttleDefaultService = new FlyShuttleDefaultService();
        }
        return flyShuttleDefaultService;
    }

    public static boolean isDoable(Player player, City destination) {
        return player.getCity().getHasCenter() && destination.getHasCenter();
    }

    public static List<Action> returnAvailableActions(Player player, List<City> citiesWithResearchCenterList) {
        List<Action> actionList = new ArrayList<>();
        if(!player.getCity().getHasCenter()) {
            return actionList;
        }
        citiesWithResearchCenterList.stream().forEach(destination -> {
            if(!player.getCity().equals(destination)) {
                actionList.add(new FlyShuttleAction(player, destination));
            }
        });
        return actionList;
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getCity().getHasCenter()) {
            throw new ActionException(ActionsType.SHUTTLEFLIGHT, SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION);
        } else if (!destination.getHasCenter()) {
            throw new ActionException(ActionsType.SHUTTLEFLIGHT, SHUTTLEFLIGHT_ERROR_DESTINY_NO_RESEARCH_STATION);
        }
        player.setCity(destination);
    }
}

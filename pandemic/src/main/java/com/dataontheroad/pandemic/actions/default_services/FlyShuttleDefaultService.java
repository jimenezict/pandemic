package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyShuttleAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if(!player.getCity().getHasCenter()) {
            return new ArrayList<>();
        }
        return citiesWithResearchCenterList.stream()
                .filter(destination -> !player.getCity().equals(destination))
                .map(destination -> new FlyShuttleAction(player, destination))
                .collect(Collectors.toList());
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

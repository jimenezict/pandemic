package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ActionServiceHelper {

    private ActionServiceHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Action> getListOfActions(Player player, List<Virus> virusList, List<City> citiesWithResearchCenter, List<Player> otherPlayersOnTheCity) {
        List<Action> allowedActions = new ArrayList<>();
        allowedActions.addAll(player.getBuildResearchCenter().returnAvailableActions(player));
        allowedActions.addAll(player.getDiscoverCure().returnAvailableActions(player, virusList));
        allowedActions.addAll(player.getFlyCharter().returnAvailableActions(player));
        allowedActions.addAll(player.getFlyDirectCity().returnAvailableActions(player));
        allowedActions.addAll(player.getFlyShuttle().returnAvailableActions(player, citiesWithResearchCenter));
        allowedActions.addAll(player.getDriveFerry().returnAvailableActions(player));
        allowedActions.addAll(player.getShareKnowledge().returnAvailableActions(player, otherPlayersOnTheCity));
        allowedActions.addAll(player.getTreatDisease().returnAvailableActions(player, virusList));
        return allowedActions;
    }

    public static Boolean executeAction(Action action) {
        try {
            action.execute();
        } catch (ActionException e) {
            return FALSE;
        }
        return TRUE;
    }
}

package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.actions.defaultServices.*;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.virus.Virus;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ActionService {

    public static List<Action> getListOfActions(Player player, List<Virus> virusList, List<City> citiesWithResearchCenter, List<Player> otherPlayersOnTheCity) {
        List<Action> allowedActions = new ArrayList<>();
        allowedActions.addAll(player.getBuildResearchCenter().returnAvailableActions(player));
        allowedActions.addAll(player.getDiscoverCure().returnAvailableActions(player, virusList));
        allowedActions.addAll(player.getFlyCharter().returnAvailableActions(player));
        allowedActions.addAll(player.getFlyDirectCity().returnAvailableActions(player));
        allowedActions.addAll(player.getFlyShuttle().returnAvailableActions(player, citiesWithResearchCenter));
        allowedActions.addAll(player.getDriveFerry().returnAvailableActions(player));
        allowedActions.addAll(player.getShareKnowledge().returnAvailableActions(player, otherPlayersOnTheCity));
        return allowedActions;
    }

    public static void printListOfActions(List<Action> getListOfActions) {
        getListOfActions.stream().forEach(action -> {System.out.println(action.actionPrompt());});
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

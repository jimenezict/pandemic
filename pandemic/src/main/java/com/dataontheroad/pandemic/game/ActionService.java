package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.actions.defaultServices.*;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.Player;
import com.dataontheroad.pandemic.board.model.Virus;
import com.dataontheroad.pandemic.exceptions.ActionException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ActionService {

    public List<Action> getListOfActions(Player player, List<Virus> virusList, List<City> citiesWithResearchCenter, List<Player> otherPlayersOnTheCity) {
        List<Action> allowedActions = new ArrayList<>();
        allowedActions.addAll(BuildResearchCenter.returnAvailableActions(player));
        allowedActions.addAll(DiscoverCure.returnAvailableActions(player, virusList));
        allowedActions.addAll(FlyCharter.returnAvailableActions(player));
        allowedActions.addAll(FlyDirectCity.returnAvailableActions(player));
        allowedActions.addAll(FlyShuttle.returnAvailableActions(player, citiesWithResearchCenter));
        allowedActions.addAll(MoveNodeCity.returnAvailableActions(player));
        allowedActions.addAll(ShareKnowledge.returnAvailableActions(player, otherPlayersOnTheCity));
        return allowedActions;
    }

    public void printListOfActions(List<Action> getListOfActions) {
        getListOfActions.stream().forEach(action -> {System.out.println(action.actionPrompt());});
    }

    public Boolean executeAction(Action action) {
        try {
            action.execute();
        } catch (ActionException e) {
            return FALSE;
        }
        return TRUE;
    }
}

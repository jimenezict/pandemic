package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DriveFerryAction;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;

public class DriveFerryDefaultService {

    public static boolean isDoable(Player player, City destination) {
        return player.getCity().getNodeCityConnection().contains(destination);
    }

    public static List<Action> returnAvailableActions(Player player) {
        List<Action> actionList = new ArrayList<>();

        player.getCity().getNodeCityConnection().stream().forEach(destination -> {
            if(!player.getCity().equals(destination)) {
                actionList.add(new DriveFerryAction(player, destination));
            }
        });
        return actionList;
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getCity().getNodeCityConnection().contains(destination)) {
            throw new ActionException(ActionsType.DRIVEFERRY, DRIVEFERRY_ERROR_NO_CONNECTION);
        }

        player.setCity(destination);
    }
}

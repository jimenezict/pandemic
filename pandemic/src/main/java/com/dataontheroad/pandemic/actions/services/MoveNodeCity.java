package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.model.Action;
import com.dataontheroad.pandemic.actions.model.FlyShuttleAction;
import com.dataontheroad.pandemic.actions.model.MoveNodeCityAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.Literals.DRIVEFERRY_ERROR_NO_CONNECTION;

public class MoveNodeCity {

    public static Boolean isDoable(Player player, City destination) {
        return player.getCity().getNodeCityConnection().contains(destination);
    }

    public static List<Action> returnAvailableActions(Player player) {
        List<Action> actionList = new ArrayList<>();

        player.getCity().getNodeCityConnection().stream().forEach(destination -> {
            if(!player.getCity().equals(destination)) {
                actionList.add(new MoveNodeCityAction(player, destination));
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

package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.actions.actionFactory.DriveFerryAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.Player;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;

public class DriveFerryDefaultService {

    public static Boolean isDoable(Player player, City destination) {
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

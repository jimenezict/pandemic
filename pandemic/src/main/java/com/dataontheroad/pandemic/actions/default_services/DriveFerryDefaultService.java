package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DriveFerryAction;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;
import static java.util.Objects.isNull;

public class DriveFerryDefaultService {

    private static DriveFerryDefaultService driveFerryDefaultService;

    private DriveFerryDefaultService() {
    }

    public static DriveFerryDefaultService getInstance() {
        if(isNull(driveFerryDefaultService)) {
            driveFerryDefaultService = new DriveFerryDefaultService();
        }
        return driveFerryDefaultService;
    }

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

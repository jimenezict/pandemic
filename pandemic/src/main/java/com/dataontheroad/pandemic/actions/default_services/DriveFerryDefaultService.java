package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DriveFerryAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;

public class DriveFerryDefaultService {

    public boolean isDoable(Player player, City destination) {
        return player.getCity().getNodeCityConnection().contains(destination);
    }

    public List<Action> returnAvailableActions(Player player) {
        return player.getCity().getNodeCityConnection().stream()
                .filter(destination -> !player.getCity().equals(destination))
                .map(destination -> new DriveFerryAction(player, destination))
                .collect(Collectors.toList());
    }

    public void doAction(Player player, City destination) throws ActionException {
        if(!player.getCity().getNodeCityConnection().contains(destination)) {
            throw new ActionException(ActionsType.DRIVEFERRY, DRIVEFERRY_ERROR_NO_CONNECTION);
        }

        player.setCity(destination);
    }
}

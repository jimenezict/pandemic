package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

public class MoveNodeCity {

    public static Boolean isDoable(Player player, City destination) {
        return player.getCity().getNodeCityConnection().contains(destination);
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getCity().getNodeCityConnection().contains(destination)) {
            throw new ActionException(ActionsType.MOVENODECITY, "Destinition is not available for the origin city");
        }

        player.setCity(destination);
    }
}

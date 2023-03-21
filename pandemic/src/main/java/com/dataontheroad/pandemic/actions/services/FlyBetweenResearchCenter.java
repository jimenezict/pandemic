package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.helper.ActionsHelper.playerHasCardForHisPosition;

public class FlyBetweenResearchCenter {
    public static Boolean isDoable(Player player, City destination) {
        return player.getCity().getHasCenter() && destination.getHasCenter();
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getCity().getHasCenter()) {
            throw new ActionException(ActionsType.FLYBETWEENRESEARCH, "Origin city has not research center");
        } else if (!destination.getHasCenter()) {
            throw new ActionException(ActionsType.FLYBETWEENRESEARCH, "Destiny city has not research center");
        }
        player.setCity(destination);
    }
}

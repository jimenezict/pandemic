package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.SHUTTLEFLIGHT_ERROR_DESTINY_NO_RESEARCH_STATION;
import static com.dataontheroad.pandemic.constants.Literals.SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION;

public class FlyShuttle {
    public static Boolean isDoable(Player player, City destination) {
        return player.getCity().getHasCenter() && destination.getHasCenter();
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getCity().getHasCenter()) {
            throw new ActionException(ActionsType.SHUTTLEFLIGHT, SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION);
        } else if (!destination.getHasCenter()) {
            throw new ActionException(ActionsType.SHUTTLEFLIGHT, SHUTTLEFLIGHT_ERROR_DESTINY_NO_RESEARCH_STATION);
        }
        player.setCity(destination);
    }
}

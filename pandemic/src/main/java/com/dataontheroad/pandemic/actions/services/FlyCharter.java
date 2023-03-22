package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.FLYCHARTER_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.Card.createCityCard;

public class FlyCharter {

    public static Boolean isDoable(Player player) {
        return player.getListCard().contains(createCityCard(player.getCity()));
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getListCard().contains(createCityCard(player.getCity()))) {
            throw new ActionException(ActionsType.FLYCHARTER, FLYCHARTER_ERROR_NO_CARD);
        }
        player.getListCard().remove(createCityCard(player.getCity()));
        player.setCity(destination);
    }
}

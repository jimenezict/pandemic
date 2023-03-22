package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.FLYDIRECT_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.Card.createCityCard;

public class FlyDirectCity {

    public static Boolean isDoable(Player player, City destination) {
        return player.getListCard().contains(createCityCard(destination));
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getListCard().contains(createCityCard(destination))) {
            throw new ActionException(ActionsType.FLYDIRECT, FLYDIRECT_ERROR_NO_CARD);
        }
        player.setCity(destination);
        player.getListCard().remove(createCityCard(destination));
    }
}

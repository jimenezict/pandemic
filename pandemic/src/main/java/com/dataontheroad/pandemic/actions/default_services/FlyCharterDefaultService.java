package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyCharterAction;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.board.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYCHARTER_ERROR_NO_CARD;

public class FlyCharterDefaultService {

    public static boolean isDoable(Player player) {
        return player.getListCard().contains(createCityCard(player.getCity()));
    }

    public static List<Action> returnAvailableActions(Player player) {
        return isDoable(player) ? new ArrayList<>(Arrays.asList(new FlyCharterAction(player))) : new ArrayList<>();
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getListCard().contains(createCityCard(player.getCity()))) {
            throw new ActionException(ActionsType.FLYCHARTER, FLYCHARTER_ERROR_NO_CARD);
        }
        player.getListCard().remove(createCityCard(player.getCity()));
        player.setCity(destination);
    }
}
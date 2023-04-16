package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyDirectAction;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.CITY;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYDIRECT_ERROR_NO_CARD;

public class FlyDirectCityDefaultService {

    public static boolean isDoable(Player player, City destination) {
        return player.getListCard().contains(createCityCard(destination));
    }

    public static List<Action> returnAvailableActions(Player player) {
        List<Action> actionList = new ArrayList<>();
        player.getListCard().stream()
                .filter(card -> CITY.equals(card.getCardType()))
                .forEach(destination -> {
                    if(!player.getCity().equals(((CityCard) destination).getCity())) {
                        actionList.add(new FlyDirectAction(player, ((CityCard) destination).getCity()));
                    }
        });
        return actionList;
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getListCard().contains(createCityCard(destination))) {
            throw new ActionException(ActionsType.FLYDIRECT, FLYDIRECT_ERROR_NO_CARD);
        }
        player.setCity(destination);
        player.getListCard().remove(createCityCard(destination));
    }
}

package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.actions.actionFactory.FlyCharterAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.constants.Literals.FLYCHARTER_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.board.model.Card.createCityCard;
import static org.springframework.util.CollectionUtils.isEmpty;

public class FlyCharter {

    public static Boolean isDoable(Player player) {
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

package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.model.Action;
import com.dataontheroad.pandemic.actions.model.BuildResearchCenterAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.helper.ActionsHelper.playerHasCardForHisPosition;
import static com.dataontheroad.pandemic.helper.ActionsHelper.playerRemoveCardFromDeck;
import static com.dataontheroad.pandemic.model.Card.createCityCard;

public class BuildResearchCenter {

    public static Boolean isDoable(Action action) {
        Player player = action.getPlayer();
        City position = player.getCity();
        return !position.getHasCenter() && playerHasCardForHisPosition(player, position);
    }

    public static List<Action> returnAvailableActions(Action action) {
        return isDoable(action)? new ArrayList<>(Arrays.asList(new BuildResearchCenterAction(action.getPlayer()))) : new ArrayList<>();
    }

    public static void doAction(Player player) throws ActionException {
        City position = player.getCity();
        if(position.getHasCenter()) {
            throw new ActionException(ActionsType.BUILDRESEARCHCENTER, "Center already created");
        } else if (!playerHasCardForHisPosition(player, position)) {
            throw new ActionException(ActionsType.BUILDRESEARCHCENTER, "Player has no card for that city");
        }
        position.setHasCenter(Boolean.TRUE);
        playerRemoveCardFromDeck(player, createCityCard(position));
    }
}

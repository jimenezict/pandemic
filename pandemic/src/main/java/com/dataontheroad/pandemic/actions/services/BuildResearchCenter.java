package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.model.Action;
import com.dataontheroad.pandemic.actions.model.BuildResearchCenterAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.constants.Literals.BUILDRESEARCHSTATION_ERROR_CENTER_CREATED;
import static com.dataontheroad.pandemic.constants.Literals.BUILDRESEARCHSTATION_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.helper.ActionsHelper.playerHasCardForHisPosition;
import static com.dataontheroad.pandemic.helper.ActionsHelper.playerRemoveCardFromDeck;
import static com.dataontheroad.pandemic.model.Card.createCityCard;

public class BuildResearchCenter {

    public static Boolean isDoable(Player player) {
        City position = player.getCity();
        return !position.getHasCenter() && playerHasCardForHisPosition(player, position);
    }

    public static List<Action> returnAvailableActions(Player player) {
        return isDoable(player)? new ArrayList<>(Arrays.asList(new BuildResearchCenterAction(player))) : new ArrayList<>();
    }

    public static void doAction(Player player) throws ActionException {
        City position = player.getCity();
        if(position.getHasCenter()) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_CENTER_CREATED);
        } else if (!playerHasCardForHisPosition(player, position)) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_NO_CARD);
        }
        position.setHasCenter(Boolean.TRUE);
        playerRemoveCardFromDeck(player, createCityCard(position));
    }
}

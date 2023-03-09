package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.helper.ActionsHelper.playerHasCardForHisPosition;
import static com.dataontheroad.pandemic.helper.ActionsHelper.playerRemoveCardFromDeck;
import static com.dataontheroad.pandemic.model.Card.createCityCard;

public class BuildResearchCenter {

    public static Boolean isDoable(Player player, City position) {
        return !position.getHasCenter() && playerHasCardForHisPosition(player, position);
    }

    public static void doAction(Player player, City position) throws ActionException {
        if(position.getHasCenter()) {
            throw new ActionException(ActionsType.BUILDRESEARCHCENTER, "Center already created");
        } else if (!playerHasCardForHisPosition(player, position)) {
            throw new ActionException(ActionsType.BUILDRESEARCHCENTER, "Player has no card for that city");
        }
        position.setHasCenter(Boolean.TRUE);
        playerRemoveCardFromDeck(player, createCityCard(position));
    }
}

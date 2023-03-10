package com.dataontheroad.pandemic.helper;

import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

public class ActionsHelper {

    public static final Boolean playerRemoveCardFromDeck(Player player, Card card) {
        return player.getListCard().remove(card);
    }

    public static final Boolean playerHasCardForHisPosition(Player player, City position) {
        return player.getListCard().contains(Card.createCityCard(position));
    }
}

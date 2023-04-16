package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

public class ActionsHelper {

    private ActionsHelper() {
        throw new IllegalStateException("Service Class");
    }

    public static final boolean playerRemoveCardFromDeck(Player player, CityCard card) {
        return player.getListCard().remove(card);
    }

    public static final boolean playerHasCardForHisPosition(Player player, City position) {
        return player.getListCard().contains(CityCard.createCityCard(position));
    }
}

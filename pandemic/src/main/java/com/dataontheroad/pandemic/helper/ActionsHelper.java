package com.dataontheroad.pandemic.helper;

import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;

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

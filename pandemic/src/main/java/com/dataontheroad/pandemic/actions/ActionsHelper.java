package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;

import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.CITY;

public class ActionsHelper {

    private ActionsHelper() {
        throw new IllegalStateException("Service Class");
    }

    public static final boolean playerRemoveCardFromHand(Player player, CityCard card) {
        return player.getListCard().remove(card);
    }

    public static final boolean playerHasCardForHisLocation(Player player, City position) {
        return player.getListCard().contains(CityCard.createCityCard(position));
    }

    public static final boolean playerHasEnoughCards(Player player, VirusType virusType) {
        return player.getListCard().stream()
                .filter(card -> CITY.equals(card.getCardType()))
                .filter(card -> ((CityCard) card).getVirus().equals(virusType))
                .count() == player.getNumOfCardsForDiscoveringCure();
    }
}

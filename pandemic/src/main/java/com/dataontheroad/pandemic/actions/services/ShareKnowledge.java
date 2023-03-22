package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.*;

public class ShareKnowledge {

    public static Boolean isDoable(Player sender, Player receiver, Card card) {
        return sender.getCity().equals(receiver.getCity())  // sender and receiver are on the same city
                && receiver.getListCard().size() < 7        // receiver has space for a new card
                && sender.getListCard().contains(card)      // sender has the card on his hand
                && sender.getCity().equals(card.getCity()); // sender shares the card where is located

    }

    public static void doAction(Player sender, Player receiver, Card card) throws ActionException {
        if(!sender.getListCard().contains(card)) {
            throw new ActionException(ActionsType.SHAREKNOWLEDGE, SHAREKNOWLEDGE_ERROR_NO_CARD);
        } else if (receiver.getListCard().size() == 7) {
            throw new ActionException(ActionsType.SHAREKNOWLEDGE, SHAREKNOWLEDGE_ERROR_OVERCAPACITY);
        } else if(!sender.getCity().equals(receiver.getCity())) {
            throw new ActionException(ActionsType.SHAREKNOWLEDGE, SHAREKNOWLEDGE_ERROR_NOT_SAME_CITY);
        } else if(!sender.getCity().equals(card.getCity())) {
            throw new ActionException(ActionsType.SHAREKNOWLEDGE, SHAREKNOWLEDGE_ERROR_NOT_CARD_CITY);
        }

        sender.getListCard().remove(card);
        receiver.getListCard().add(card);
    }
}

package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.actions.actionFactory.ShareKnowledgeAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.Player;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.Literals.*;
import static com.dataontheroad.pandemic.helper.ActionsHelper.playerHasCardForHisPosition;

public class ShareKnowledge {

    public static Boolean isDoable(Player sender, Player receiver, Card card) {
        return sender.getCity().equals(receiver.getCity())  // sender and receiver are on the same city
                && receiver.getListCard().size() < 7        // receiver has space for a new card
                && sender.getListCard().contains(card)      // sender has the card on his hand
                && sender.getCity().equals(card.getCity()); // sender shares the card where is located

    }

    public static List<Action> returnAvailableActions(Player player, List<Player> playersList) {
        // player is not on the playersList
        List<Action> actionList = new ArrayList<>();
        Player sender;
        Card card = Card.createCityCard(player.getCity());
        if(playerHasCardForHisPosition(player, player.getCity())) {
            sender = player;
        } else {
            if(playersList.stream().filter(player1 -> playerHasCardForHisPosition(player1, player1.getCity())).count() == 0) {
                return actionList;
            }
            sender = playersList.stream().filter(player1 -> playerHasCardForHisPosition(player1, player1.getCity())).findFirst().get();
            playersList.remove(sender);
            playersList.add(player);
        }

        playersList.stream().forEach(receiver -> {
            if(isDoable(sender, receiver, card)) {
                actionList.add(new ShareKnowledgeAction(sender, receiver, card));
            }
        });

        return actionList;
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

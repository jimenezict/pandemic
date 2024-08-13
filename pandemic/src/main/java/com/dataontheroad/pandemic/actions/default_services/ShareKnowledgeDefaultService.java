package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.ShareKnowledgeAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsHelper.playerHasCardForHisLocation;
import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static java.util.Objects.isNull;

public class ShareKnowledgeDefaultService {

    private static ShareKnowledgeDefaultService shareKnowledgeDefaultService;

    protected ShareKnowledgeDefaultService() {


    }

    public static ShareKnowledgeDefaultService getInstance() {
        if(isNull(shareKnowledgeDefaultService)) {
            shareKnowledgeDefaultService = new ShareKnowledgeDefaultService();
        }
        return shareKnowledgeDefaultService;
    }

    public boolean isDoable(Player sender, Player receiver, CityCard card) {
        return sender.getCity().equals(receiver.getCity())  // sender and receiver are on the same city
                && receiver.getListCard().size() < 7        // receiver has space for a new card
                && sender.getListCard().contains(card)      // sender has the card on his hand
                && sender.getCity().equals(card.getCity()); // sender shares the card where is located

    }

    public List<Action> returnAvailableActions(Player player, List<Player> playersList) {
        // player is not on the playersList
        List<Action> actionList = new ArrayList<>();
        Player sender;
        CityCard card = CityCard.createCityCard(player.getCity());
        if(playerHasCardForHisLocation(player, player.getCity())) {
            sender = player;
        } else {
            if(playersList.stream().noneMatch(player1 -> playerHasCardForHisLocation(player1, player1.getCity()))) {
                return actionList;
            }
            sender = playersList.stream().filter(player1 -> playerHasCardForHisLocation(player1, player1.getCity())).findFirst().get();
            playersList.remove(sender);
            playersList.add(player);
        }

        playersList.forEach(receiver -> {
            if(isDoable(sender, receiver, card)) {
                actionList.add(new ShareKnowledgeAction(sender, receiver, card));
            }
        });

        return actionList;
    }

    public void doAction(Player sender, Player receiver, CityCard card) throws ActionException {
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

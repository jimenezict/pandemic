package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.ShareKnowledgeAction;
import com.dataontheroad.pandemic.actions.default_services.ShareKnowledgeDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.ResearchPlayer;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.CITY;

public class ShareKnowledgeResearchService extends ShareKnowledgeDefaultService {

    public boolean isDoable(Player sender, Player receiver, CityCard card) {
        return sender.getCity().equals(receiver.getCity())  // sender and receiver are on the same city
                && receiver.getListCard().size() < 7        // receiver has space for a new card
                && sender.getListCard().contains(card);     // sender has the card on his hand
    }

    public List<Action> returnAvailableActions(ResearchPlayer researchPlayer, List<Player> playersList) {
        List<Action> actionList = new ArrayList<>();

        //when researchPlayer is the sender and any of the others on the same city are the receivers
        playersList.stream()
                .forEach(receiver ->
                    researchPlayer.getListCard().stream()
                            .filter(card -> CITY.equals(card.getCardType()))
                            .filter(card -> isDoable(researchPlayer, receiver, (CityCard) card))
                            .forEach(card -> actionList.add(new ShareKnowledgeAction(researchPlayer, receiver, (CityCard) card)))
                );

        //when any of the others on the same city are the senders and the researchPlayer is the receiver
        playersList.stream()
                .forEach(sender ->
                    sender.getListCard().stream()
                            .filter(card -> CITY.equals(card.getCardType()))
                            .filter(card -> isDoable(sender, researchPlayer, (CityCard) card))
                            .forEach(card -> actionList.add(new ShareKnowledgeAction(sender, researchPlayer, (CityCard) card)))

                );

        return actionList;
    }

    public void doAction(Player sender, Player receiver, CityCard card) throws ActionException {
        if(!sender.getListCard().contains(card)) {
            throw new ActionException(ActionsType.SHAREKNOWLEDGE, SHAREKNOWLEDGE_ERROR_NO_CARD);
        } else if (receiver.getListCard().size() == 7) {
            throw new ActionException(ActionsType.SHAREKNOWLEDGE, SHAREKNOWLEDGE_ERROR_OVERCAPACITY);
        } else if(!sender.getCity().equals(receiver.getCity())) {
            throw new ActionException(ActionsType.SHAREKNOWLEDGE, SHAREKNOWLEDGE_ERROR_NOT_SAME_CITY);
        }

        sender.getListCard().remove(card);
        receiver.getListCard().add(card);
    }
}

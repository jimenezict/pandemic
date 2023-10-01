package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.TakeDiscardEventCardAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.CONTINGENCY_ERROR_HAS_EXTRA_CARD_ALREADY;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EVENT_ACTION;
import static java.util.Objects.isNull;

public class ContingencyPlannerService {

    /**
     * Based on the status of the game, will decide if Contingency Player can get a discarded Card
     * It is doable when the player has not already an extra event card and when there are discarded
     * actions on the discard player deck.
     *
     * @param player         Player should be a Contingency Planner
     * @param discardedCards Discarded cards deck, to validate that there are available actions
     * @return the boolean
     */
    public static boolean isDoable(ContingencyPlayer player, List<BaseCard> discardedCards) {
        return isNull(player.getExtraEventCard())
                && !isNull(discardedCards)
                && discardedCards.stream().filter(card -> EVENT_ACTION.equals(card.getCardType())).count() > 0;
    }

    /**
     * In case the player do not hold an extra event card, generates an Action for each one of the Action cards discarded
     *
     * @param player         Player should be a Contingency Planner
     * @param discardedCards tDiscarded cards deck, to validate that there are available actions
     * @return a list of TAKEDISCARDEVENTCARD Available actions
     * @throws ActionException the action exception
     */
    public static List<Action> returnAvailableActions(ContingencyPlayer player, List<BaseCard> discardedCards) {

        List<Action> availableActions = new ArrayList<>();
        if(isNull(player.getExtraEventCard())) {
            availableActions = discardedCards
                    .stream()
                    .filter(card -> EVENT_ACTION.equals(card.getCardType()))
                    .map(card -> {
                        try {
                            return new TakeDiscardEventCardAction(player, (SpecialCard) card);
                        } catch (ActionException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());
        }

        return availableActions;
    }

    /**
     * Executes the action of getting the card from the discarded deck and bring it to the hand of the player
     * consequently, the extra slot is busy and this action is unabled until is used that card
     * In case the extra card is used, disapear from the game
     *
     * @param player         Player should be a Contingency Planner
     * @param discardedCards Cards on the discarded player Deck
     * @param eventCard      Event card that will be collected by the Contingency Planner. E.g: "Government Grant"
     * @throws ActionException In case that the player has already an extra card on his hand
     */
    public static void doAction(ContingencyPlayer player, List<BaseCard> discardedCards, SpecialCard eventCard) throws ActionException {
        if(!isNull(player.getExtraEventCard())) {
            throw new ActionException(ActionsType.PLAYERACTION, CONTINGENCY_ERROR_HAS_EXTRA_CARD_ALREADY);
        }

        int index = discardedCards.indexOf(eventCard);
        if(index > 0) {
            discardedCards.remove(discardedCards.indexOf(eventCard));
            player.setExtraEventCard(eventCard);
        }
    }
}

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

import static com.dataontheroad.pandemic.constants.LiteralsAction.CONTINGENCY_ERROR_HAS_EXTRA_CARD_ALREADY;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EVENT_ACTION;
import static java.util.Objects.isNull;

public class ContingencyPlannerService {
    private static ContingencyPlannerService contingencyPlannerService;


    public static ContingencyPlannerService getInstance() {
        if(isNull(contingencyPlannerService)) {
            contingencyPlannerService = new ContingencyPlannerService();
        }
        return contingencyPlannerService;
    }

    public static boolean isDoable(ContingencyPlayer player, List<BaseCard> discardedCards) {
        if(isNull(player.getExtraEventCard())) {
            if(!isNull(discardedCards)) {
                if(discardedCards.stream().filter(card -> EVENT_ACTION.equals(card.getCardType())).count() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Action> returnAvailableActions(ContingencyPlayer player, List<BaseCard> discardedCards) throws ActionException {

        List<Action> availableActions = new ArrayList<>();
        for (BaseCard card : discardedCards) {
            if (EVENT_ACTION.equals(card.getCardType())) {
                availableActions.add(new TakeDiscardEventCardAction(player, (SpecialCard) card));
            }
        }

        return availableActions;
    }

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

package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.CONTINGENCY_ERROR_HAS_EXTRA_CARD_ALREADY;
import static com.dataontheroad.pandemic.constants.LiteralsAction.CONTINGENCY_ERROR_NO_EVENTS_CARDS;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EVENT_ACTION;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class ContingencyPlannerService {
    private static ContingencyPlannerService contingencyPlannerService;


    public static ContingencyPlannerService getInstance() {
        if(isNull(contingencyPlannerService)) {
            contingencyPlannerService = new ContingencyPlannerService();
        }
        return contingencyPlannerService;
    }

    public static boolean isDoable(ContingencyPlayer player, List<BaseCard> discardedCards) {
        if(!isNull(player.getExtraEventCard())) {
            if(!isNull(discardedCards)) {
                if(discardedCards.stream().filter(card -> EVENT_ACTION.equals(card.getCardType())).count() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Action> returnAvailableActions(ContingencyPlayer player, List<BaseCard> discardedCards) throws ActionException {
        if(isNull(player.getExtraEventCard())) {
            throw new ActionException(ActionsType.PLAYERACTION, CONTINGENCY_ERROR_HAS_EXTRA_CARD_ALREADY);
        }

        if(isNull(discardedCards)) {
            throw new ActionException(ActionsType.PLAYERACTION, CONTINGENCY_ERROR_NO_EVENTS_CARDS);
        }

        List<BaseCard> availableEvents = discardedCards.stream().filter(card -> EVENT_ACTION.equals(card.getCardType())).collect(Collectors.toList());

        if(isEmpty(availableEvents)) {
            throw new ActionException(ActionsType.PLAYERACTION, CONTINGENCY_ERROR_NO_EVENTS_CARDS);
        }
        return null;
    }

    public static void doAction() {

    }
}

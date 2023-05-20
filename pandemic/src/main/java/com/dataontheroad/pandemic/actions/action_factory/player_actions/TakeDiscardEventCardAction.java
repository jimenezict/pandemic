package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;

import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.TAKEDISCARDEVENTCARD;
import static com.dataontheroad.pandemic.constants.LiteralsAction.CONTINGENCY_ERROR_NO_CONTINGENCY_PLAYER;
import static com.dataontheroad.pandemic.constants.LiteralsAction.TAKEDISCARDEVENTCARD_ACTION;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.CONTINGENCY_NAME;

public class TakeDiscardEventCardAction extends Action {

    private final SpecialCard eventCard;
    private List<BaseCard> discardedCards;

    /**
     * Creates an Action of type TAKEDISCARDEVENTCARD.
     * Which extends from Action but has a property for an additional EventCard.
     * TakeDiscardEventCardAction includes a field for discarded cards but needs to be set out of the
     * constructor with a setter
     *
     * @param player    Player should be a Contingency Planner
     * @param eventCard Event card that will be collected by the Contingency Planner. E.g: "Government Grant"
     * @throws ActionException When player is not a Contingency Planner
     */
    public TakeDiscardEventCardAction(ContingencyPlayer player, SpecialCard eventCard) throws ActionException {
        super(TAKEDISCARDEVENTCARD, player);
        if(!CONTINGENCY_NAME.equals(player.getName())){
            throw new ActionException(TAKEDISCARDEVENTCARD, CONTINGENCY_ERROR_NO_CONTINGENCY_PLAYER);
        }
        this.eventCard = eventCard;
    }

    @Override
    public String actionPrompt() {
        return TAKEDISCARDEVENTCARD_ACTION + eventCard.getEventName();
    }

    @Override
    public void execute() throws ActionException {
        ContingencyPlayer player = (ContingencyPlayer) getPlayer();
        player.specialActionService().doAction(player, discardedCards, eventCard);
    }

    public void setDiscardedCards(List<BaseCard> discardedCards) {
        this.discardedCards = discardedCards;
    }

    public SpecialCard getEventCard() {
        return eventCard;
    }
}

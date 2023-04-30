package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import com.dataontheroad.pandemic.model.player.Player;

import static com.dataontheroad.pandemic.constants.LiteralsAction.CONTINGENCY_ERROR_NO_CONTINGENCY_PLAYER;
import static com.dataontheroad.pandemic.constants.LiteralsAction.TAKEDISCARDEVENTCARD_ACTION;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.CONTINGENCY_NAME;

public class TakeDiscardEventCardAction extends Action {

    private final SpecialCard eventCard;

    public TakeDiscardEventCardAction(Player player, SpecialCard eventCard) throws ActionException {
        super(ActionsType.TAKEDISCARDEVENTCARD, player);
        if(!CONTINGENCY_NAME.equals(player.getName())){
            throw new ActionException(ActionsType.TAKEDISCARDEVENTCARD, CONTINGENCY_ERROR_NO_CONTINGENCY_PLAYER);
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
        player.specialActionService().doAction();
    }

    public SpecialCard getEventCard() {
        return eventCard;
    }
}

package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import com.dataontheroad.pandemic.model.player.Player;

import static com.dataontheroad.pandemic.constants.LiteralsAction.TAKEDISCARDEVENTCARD_ACTION;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.CONTINGENCY_NAME;

public class TakeDiscardEventCardAction extends Action {

    SpecialCard eventCard;

    public TakeDiscardEventCardAction(Player player, SpecialCard eventCard) {
        super(ActionsType.TAKEDISCARDEVENTCARD, player);
        assert(CONTINGENCY_NAME.equals(player.getName()));
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
}

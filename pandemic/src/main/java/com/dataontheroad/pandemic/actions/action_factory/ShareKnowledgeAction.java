package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.player.Player;

import static com.dataontheroad.pandemic.constants.LiteralsAction.SHAREKNOWLEDGE_ACTION;

public class ShareKnowledgeAction extends Action {

    Player receiver;
    CityCard card;

    public ShareKnowledgeAction(Player sender, Player receiver, CityCard card) {
        super(ActionsType.SHAREKNOWLEDGE, sender);
        this.receiver = receiver;
        this.card = card;
    }

    public Player getSender() {
        return player;
    }

    public Player getReceiver() {
        return receiver;
    }

    @Override
    public String actionPrompt() {
        return SHAREKNOWLEDGE_ACTION + player;
    }

    @Override
    public void execute() throws ActionException {
        this.player.getShareKnowledge().doAction(player, receiver, card);
    }
}

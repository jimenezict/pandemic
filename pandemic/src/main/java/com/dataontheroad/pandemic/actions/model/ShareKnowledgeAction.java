package com.dataontheroad.pandemic.actions.model;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.DRIVEFERRY_ACTION;
import static com.dataontheroad.pandemic.constants.Literals.SHAREKNOWLEDGE_ACTION;

public class ShareKnowledgeAction extends Action {

    Player receiver;
    Card card;

    public ShareKnowledgeAction(Player sender, Player receiver, Card card) {
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
}

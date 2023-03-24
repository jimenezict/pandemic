package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.model.Player;

public abstract class Action {
    final ActionsType actionsType;
    final Player player;

    Action(ActionsType actionsType, Player player) {
        this.actionsType = actionsType;
        this.player = player;
    }

    public abstract String actionPrompt();

    public ActionsType getActionsType() {
        return actionsType;
    }

    public Player getPlayer() {
        return player;
    }
}

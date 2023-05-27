package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.player.Player;

public abstract class Action {
    final ActionsType actionsType;
    protected final Player player;

    protected Action(ActionsType actionsType, Player player) {
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

    public abstract void execute() throws ActionException;
}

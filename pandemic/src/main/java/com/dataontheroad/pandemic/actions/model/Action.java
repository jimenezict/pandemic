package com.dataontheroad.pandemic.actions.model;

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


}

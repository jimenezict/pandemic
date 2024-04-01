package com.dataontheroad.pandemic.game.persistence.model;

import com.dataontheroad.pandemic.model.player.Player;

import java.io.Serializable;

public class TurnInformation implements Serializable {

    private int missingTurns;
    private Player activePlayer;
    private static final int NUM_MISSING_ACTIONS = 4;

    public TurnInformation(Player activePlayer) {
        this.activePlayer = activePlayer;
        missingTurns = NUM_MISSING_ACTIONS;
    }

    public void setNewTurn(Player activePlayer) {
        this.activePlayer = activePlayer;
        missingTurns = NUM_MISSING_ACTIONS;
    }

    public boolean canDoNextActionAndReduceMissingTurns() {
        this.missingTurns = this.missingTurns - 1;
        return missingTurns!=0;
    }

    public int getMissingTurns() {
        return missingTurns;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
}

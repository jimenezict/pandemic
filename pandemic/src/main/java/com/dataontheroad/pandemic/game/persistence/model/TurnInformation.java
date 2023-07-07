package com.dataontheroad.pandemic.game.persistence.model;

import com.dataontheroad.pandemic.model.player.Player;

public class TurnInformation {

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
        if(missingTurns==0)
            return false;
        else {
            this.missingTurns = this.missingTurns - 1;
            return true;
        }
    }

    public int getMissingTurns() {
        return missingTurns;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
}

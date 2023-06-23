package com.dataontheroad.pandemic.game.persistence.model;

import com.dataontheroad.pandemic.model.player.Player;

public class TurnInformation {

    private int missingTurns;
    private Player activePlayer;

    public TurnInformation(Player activePlayer) {
        this.activePlayer = activePlayer;
        missingTurns = 2;
    }

    public void setNewTurn(Player activePlayer) {
        this.activePlayer = activePlayer;
        missingTurns = 2;
    }

    public boolean canDoNextAction() {
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

package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;

public class TurnResponseDTO {

    private int missingTurns;
    private Player activePlayer;

    private List<Action> actionList;

    public void setTurnInformation(TurnInformation turnInformation, List<Action> actionList
     ) {
        this.missingTurns = turnInformation.getMissingTurns();
        this.activePlayer = turnInformation.getActivePlayer();
        this.actionList = actionList;
    }

    public int getMissingTurns() {
        return missingTurns;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<Action> getActionList() {
        return actionList;
    }

}

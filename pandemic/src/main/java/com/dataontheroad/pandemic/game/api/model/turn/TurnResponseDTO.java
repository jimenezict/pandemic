package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;

import java.util.List;

import static com.dataontheroad.pandemic.game.service.converters.ConvertTurnDTO.convertTurnResponsePlayer;

public class TurnResponseDTO {

    private int missingTurns;
    private TurnResponsePlayer activePlayer;
    private List<Action> actionList;

    public void setTurnInformation(TurnInformation turnInformation, List<Action> actionList
     ) {
        this.missingTurns = turnInformation.getMissingTurns();
        this.activePlayer = convertTurnResponsePlayer(turnInformation.getActivePlayer());
        this.actionList = actionList;
    }

    public int getMissingTurns() {
        return missingTurns;
    }

    public TurnResponsePlayer getActivePlayer() {
        return activePlayer;
    }

    public List<Action> getActionList() {
        return actionList;
    }

}

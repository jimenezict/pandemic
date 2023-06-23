package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.model.player.Player;

public class TurnResponseDTO {

    private int missingTurns;
    private Player activePlayer;

    public void setTurnInformation(TurnInformation turnInformation) {
        this.missingTurns = turnInformation.getMissingTurns();
        this.activePlayer = turnInformation.getActivePlayer();
    }
}

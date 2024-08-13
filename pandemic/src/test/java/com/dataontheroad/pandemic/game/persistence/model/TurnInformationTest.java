package com.dataontheroad.pandemic.game.persistence.model;

import com.dataontheroad.pandemic.model.player.OperationsPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.ScientistPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnInformationTest {

    TurnInformation turnInformation;
    final Player player = new OperationsPlayer();

    @Test
    void constructor() {
        turnInformation = new TurnInformation(player);

        assertEquals(4, turnInformation.getMissingTurns());
        assertEquals(player, turnInformation.getActivePlayer());
    }

    @Test
    void executesTwiceAndReleaseToOtherPlayer() {
        turnInformation = new TurnInformation(player);

        assertTrue(turnInformation.canDoNextActionAndReduceMissingTurns());
        assertEquals(3, turnInformation.getMissingTurns());
        assertEquals(player, turnInformation.getActivePlayer());

        assertTrue(turnInformation.canDoNextActionAndReduceMissingTurns());
        assertEquals(2, turnInformation.getMissingTurns());
        assertEquals(player, turnInformation.getActivePlayer());

        assertTrue(turnInformation.canDoNextActionAndReduceMissingTurns());
        assertEquals(1, turnInformation.getMissingTurns());
        assertEquals(player, turnInformation.getActivePlayer());

        assertFalse(turnInformation.canDoNextActionAndReduceMissingTurns());
        assertEquals(0, turnInformation.getMissingTurns());
        assertEquals(player, turnInformation.getActivePlayer());

        turnInformation.setNewTurn(new ScientistPlayer());
        assertEquals(4, turnInformation.getMissingTurns());
    }

}
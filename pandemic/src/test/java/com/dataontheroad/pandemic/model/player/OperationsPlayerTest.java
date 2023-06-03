package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.player_services.OperationsBuildResearchCenterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;
import static org.junit.jupiter.api.Assertions.*;

class OperationsPlayerTest {

    OperationsPlayer player;

    @BeforeEach
    public void setUp() {
        player = new OperationsPlayer();
    }

    @Test
    void operationsPlayerConstructor() {
        assertEquals(OPERATIONS_COLOR, player.getColor());
        assertEquals(OPERATIONS_NAME, player.getName());
        assertEquals(OPERATIONS_DESCRIPTION, player.getDescription());
    }

    @Test
    void canPlayerExecuteActionThisTurn() {
        assertTrue(player.canPlayerExecuteActionThisTurn());
    }

    @Test
    void resetActionAvailable() {
        player.resetActionAvailable();
        assertTrue(player.canPlayerExecuteActionThisTurn());
    }

    @Test
    void getBuildResearchCenter() {
        assertInstanceOf(OperationsBuildResearchCenterService.class, player.getBuildResearchCenter());
    }
}
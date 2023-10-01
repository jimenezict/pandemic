package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.player_services.ContingencyPlannerService;
import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;
import static org.junit.jupiter.api.Assertions.*;

class ContingencyPlayerTest {

    @Test
    void contingencyPlayerConstructor() {
        ContingencyPlayer contingencyPlayer = new ContingencyPlayer();
        //validates also getExtraEventCard is null by default
        assertNull(contingencyPlayer.getExtraEventCard());
        assertEquals(CONTINGENCY_COLOR, contingencyPlayer.getColor());
        assertEquals(CONTINGENCY_NAME, contingencyPlayer.getName());
        assertEquals(CONTINGENCY_DESCRIPTION, contingencyPlayer.getDescription());
    }

    @Test
    void getSpecialActionService() {
        ContingencyPlayer contingencyPlayer = new ContingencyPlayer();
        assertInstanceOf(ContingencyPlannerService.class, contingencyPlayer.specialActionService() );
    }


}
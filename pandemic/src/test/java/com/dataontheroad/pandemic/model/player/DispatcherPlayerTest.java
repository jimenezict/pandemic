package com.dataontheroad.pandemic.model.player;

import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DispatcherPlayerTest {

    @Test
    void createDispatcher() {
        DispatcherPlayer dispatcherPlayer = new DispatcherPlayer();
        assertEquals(5, dispatcherPlayer.getNumOfCardsForDiscoveringCure());
        assertEquals(DISPATCHER_NAME, dispatcherPlayer.getName());
        assertEquals(DISPATCHER_COLOR, dispatcherPlayer.getColor());
        assertEquals(DISPATCHER_DESCRIPTION, dispatcherPlayer.getDescription());
    }

}
package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.SHUTTLEFLIGHT_ERROR_DESTINY_NO_RESEARCH_STATION;
import static com.dataontheroad.pandemic.constants.LiteralsAction.SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class   FlyBetweenResearchCenterTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    FlyShuttleDefaultService flyShuttleDefaultService = new FlyShuttleDefaultService();

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(newyork);
    }

    @Test
    void isDoable_originCityHasNoResearchCenter_thenFalse() {
        assertFalse(flyShuttleDefaultService.isDoable(player, calculta));
    }

    @Test
    void isDoable_originCityHasResearchCenterButNotDestiny_thenFalse() {
        newyork.setHasCenter(TRUE);
        assertFalse(flyShuttleDefaultService.isDoable(player, calculta));
    }

    @Test
    void isDoable_originCityHasResearchCenterAndDestiny_thenTrue() {
        newyork.setHasCenter(TRUE);
        calculta.setHasCenter(TRUE);
        assertTrue(flyShuttleDefaultService.isDoable(player, calculta));
    }

    @Test
    void doAction_originCityHasNoResearchCenter_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> flyShuttleDefaultService.doAction(player, calculta));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHUTTLEFLIGHT.label));
        assertTrue(actualMessage.contains(SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION));
    }

    @Test
    void doAction_originCityHasResearchCenterButNotDestiny_throwException() {
        newyork.setHasCenter(TRUE);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> flyShuttleDefaultService.doAction(player, calculta));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHUTTLEFLIGHT.label));
        assertTrue(actualMessage.contains(SHUTTLEFLIGHT_ERROR_DESTINY_NO_RESEARCH_STATION));
    }

}
package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.actions.services.FlyBetweenResearchCenter;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class FlyBetweenResearchCenterTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(newyork);
    }

    @Test
    public void isDoable_originCityHasNoResearchCenter_thenFalse() {
        assertFalse(FlyBetweenResearchCenter.isDoable(player, calculta));
    }

    @Test
    public void isDoable_originCityHasResearchCenterButNotDestiny_thenFalse() {
        newyork.setHasCenter(TRUE);
        assertFalse(FlyBetweenResearchCenter.isDoable(player, calculta));
    }

    @Test
    public void isDoable_originCityHasResearchCenterButNotDestiny_thenTrue() {
        newyork.setHasCenter(TRUE);
        calculta.setHasCenter(TRUE);
        assertTrue(FlyBetweenResearchCenter.isDoable(player, calculta));
    }

    @Test
    public void doAction_originCityHasNoResearchCenter_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> FlyBetweenResearchCenter.doAction(player, calculta));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.FLYBETWEENRESEARCH.label));
        assertTrue(actualMessage.contains("Origin city has not research center"));
    }

    @Test
    public void doAction_originCityHasResearchCenterButNotDestiny_throwException() {
        newyork.setHasCenter(TRUE);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> FlyBetweenResearchCenter.doAction(player, calculta));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.FLYBETWEENRESEARCH.label));
        assertTrue(actualMessage.contains("Destiny city has not research center"));
    }

    @Test
    public void doAction_originCityHasResearchCenterButNotDestiny() throws ActionException  {
        newyork.setHasCenter(TRUE);
        calculta.setHasCenter(TRUE);

        FlyBetweenResearchCenter.doAction(player, calculta);
    }

}
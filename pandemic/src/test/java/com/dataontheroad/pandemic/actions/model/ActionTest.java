package com.dataontheroad.pandemic.actions.model;

import com.dataontheroad.pandemic.actions.services.BuildResearchCenter;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.BUILDRESEARCHCENTER;
import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.CollectionUtils.isEmpty;

class ActionTest {

    Player player;
    List<City> emptyNodeCityConnection = new ArrayList<>();
    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);

    Action action;

    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setCity(newyork);
        player.getListCard().add(createCityCard(newyork));
        action = new BuildResearchCenterAction(player);
    }

    @Test
    public void buildResearchCenterAction_isDoable_returnsAction() {
        List<Action> availableActions = BuildResearchCenter.returnAvailableActions(action);
        assertEquals(1, availableActions.size());
        assertEquals(BUILDRESEARCHCENTER, availableActions.get(0).actionsType);
        assertEquals("Build Research Center at city: New York", availableActions.get(0).actionPrompt());
    }

    @Test
    public void buildResearchCenterAction_isNotDoable_returnsAction() {
        player.getCity().setHasCenter(Boolean.TRUE);
        List<Action> availableActions = BuildResearchCenter.returnAvailableActions(action);
        assertTrue(isEmpty(availableActions));
    }

}
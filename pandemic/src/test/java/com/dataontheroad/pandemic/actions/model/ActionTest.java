package com.dataontheroad.pandemic.actions.model;

import com.dataontheroad.pandemic.actions.services.BuildResearchCenter;
import com.dataontheroad.pandemic.actions.services.DiscoverCure;
import com.dataontheroad.pandemic.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.BUILDRESEARCHCENTER;
import static com.dataontheroad.pandemic.actions.ActionsType.DISCOVERCURE;
import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.CollectionUtils.isEmpty;

class ActionTest {

    Player player;
    List<City> emptyNodeCityConnection = new ArrayList<>();
    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calcuta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City madrid = new City("Madrid", VirusType.BLUE, emptyNodeCityConnection);
    private City paris = new City("Paris", VirusType.BLUE, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private City atlanta = new City("Atlanta", VirusType.BLUE, emptyNodeCityConnection);

    private Virus blueVirus = new Virus(VirusType.BLUE);
    private Virus blackVirus = new Virus(VirusType.BLACK);
    private Virus redVirus = new Virus(VirusType.RED);
    private Virus yellowVirus = new Virus(VirusType.YELLOW);
    List<Virus> virusList;


    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setCity(newyork);
        player.getListCard().add(createCityCard(newyork));
        virusList = Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
    }

    @Test
    public void buildResearchCenterAction_isDoable_returnsAction() {
        List<Action> availableActions = BuildResearchCenter.returnAvailableActions(player);
        assertEquals(1, availableActions.size());
        assertEquals(BUILDRESEARCHCENTER, availableActions.get(0).actionsType);
        assertEquals("Build Research Center at city: New York", availableActions.get(0).actionPrompt());
    }

    @Test
    public void buildResearchCenterAction_isNotDoable_returnsAction() {
        player.getCity().setHasCenter(Boolean.TRUE);
        List<Action> availableActions = BuildResearchCenter.returnAvailableActions(player);
        assertTrue(isEmpty(availableActions));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards_returnsAction() {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        player.getCity().setHasCenter(Boolean.TRUE);

        List<Action> availableActions = DiscoverCure.returnAvailableActions(player, virusList);
        assertEquals(1, availableActions.size());
        assertEquals(DISCOVERCURE, availableActions.get(0).actionsType);
        assertEquals("Discover a Cure for: BLUE", availableActions.get(0).actionPrompt());
    }

    @Test
    public void isDoable_cityHasNoResearchCenterCureIsNotDiscoveredHas5BlueCards_returnsAction() {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        player.getCity().setHasCenter(Boolean.FALSE);

        List<Action> availableActions = DiscoverCure.returnAvailableActions(player, virusList);
        assertEquals(0, availableActions.size());
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsDiscoveredHas5BlueCards_returnsAction() {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        player.getCity().setHasCenter(Boolean.TRUE);
        virusList.get(0).cureHasBeenDiscovered();

        List<Action> availableActions = DiscoverCure.returnAvailableActions(player, virusList);
        assertEquals(0, availableActions.size());
    }
}
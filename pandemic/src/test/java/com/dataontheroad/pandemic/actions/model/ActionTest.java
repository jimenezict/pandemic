package com.dataontheroad.pandemic.actions.model;

import com.dataontheroad.pandemic.actions.services.*;
import com.dataontheroad.pandemic.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.*;
import static com.dataontheroad.pandemic.constants.Literals.*;
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
    private City paris = new City("Paris", VirusType.BLUE, Arrays.asList(essen, madrid));
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
        calcuta.setHasCenter(Boolean.FALSE);
        newyork.setHasCenter(Boolean.FALSE);
        essen.setHasCenter(Boolean.FALSE);
    }

    @Test
    public void buildResearchCenterAction_isDoable_returnsAction() {
        List<Action> availableActions = BuildResearchCenter.returnAvailableActions(player);
        assertEquals(1, availableActions.size());
        assertEquals(BUILDRESEARCHSTATION, availableActions.get(0).actionsType);
        assertEquals(BUILDRESEARCHSTATION_ACTION + "New York", availableActions.get(0).actionPrompt());
    }

    @Test
    public void buildResearchCenterAction_isNotDoable_returnsAction() {
        player.getCity().setHasCenter(Boolean.TRUE);
        List<Action> availableActions = BuildResearchCenter.returnAvailableActions(player);
        assertTrue(isEmpty(availableActions));
    }

    @Test
    public void discoverCure_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards_returnsAction() {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        player.getCity().setHasCenter(Boolean.TRUE);

        List<Action> availableActions = DiscoverCure.returnAvailableActions(player, virusList);
        assertEquals(1, availableActions.size());
        assertEquals(DISCOVERCURE, availableActions.get(0).actionsType);
        assertEquals(DISCOVERCURE_ACTION + "BLUE", availableActions.get(0).actionPrompt());
    }

    @Test
    public void discoverCure_cityHasNoResearchCenterCureIsNotDiscoveredHas5BlueCards_returnsAction() {
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
    public void discoverCure_cityHasResearchCenterCureIsDiscoveredHas5BlueCards_returnsAction() {
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

    @Test
    public void flyCharter_playerHasCityCardThenCanFlyAnywhere_returnsAction() {
        List<Action> availableActions = FlyCharter.returnAvailableActions(player);
        assertEquals(1, availableActions.size());
        assertEquals(FLYCHARTER, availableActions.get(0).actionsType);
        assertEquals(FLYCHARTER_ACTION, availableActions.get(0).actionPrompt());
    }

    @Test
    public void flyCharter_playerHasNoCityCardThenCannotFlyAnywhere_returnsAction() {
        player.setCity(madrid);
        List<Action> availableActions = FlyCharter.returnAvailableActions(player);
        assertEquals(0, availableActions.size());
    }


    @Test
    public void flyDirect_playerHave3ValidCardsToTravel_returnsActions() {
        //player is new york and has card for essen paris madrid
        List<Card> listCard = player.getListCard();
        listCard.clear();
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        List<Action> availableActions = FlyDirectCity.returnAvailableActions(player);
        assertEquals(3, availableActions.size());
        assertEquals(FLYDIRECT, availableActions.get(0).actionsType);
        assertEquals(FLYDIRECT_ACTION + "Essen", availableActions.get(0).actionPrompt());
    }

    @Test
    public void flyDirect_playerHave2ValidCardsToTravel_returnsActions() {
        List<Card> listCard = player.getListCard();
        //player is new york and has card for new york paris madrid
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        List<Action> availableActions = FlyDirectCity.returnAvailableActions(player);
        assertEquals(2, availableActions.size());
        assertEquals(FLYDIRECT, availableActions.get(0).actionsType);
        assertEquals(FLYDIRECT_ACTION + "Paris", availableActions.get(0).actionPrompt());
    }

    @Test
    public void flyShuttle_playerIsNotOnResearchCenter_returnsActions() {
        calcuta.setHasCenter(Boolean.TRUE);
        newyork.setHasCenter(Boolean.FALSE);
        essen.setHasCenter(Boolean.TRUE);
        List<City> citiesWithLabs = Arrays.asList(calcuta, essen);
        List<Action> availableActions = FlyShuttle.returnAvailableActions(player, citiesWithLabs);
        assertEquals(0, availableActions.size());
    }

    @Test
    public void flyShuttle_playerIsOnResearchCenter_returnsActions() {
        calcuta.setHasCenter(Boolean.TRUE);
        newyork.setHasCenter(Boolean.TRUE);
        essen.setHasCenter(Boolean.TRUE);
        List<City> citiesWithLabs = Arrays.asList(calcuta, essen);
        List<Action> availableActions = FlyShuttle.returnAvailableActions(player, citiesWithLabs);
        assertEquals(2, availableActions.size());
        assertEquals(SHUTTLEFLIGHT, availableActions.get(0).actionsType);
        assertEquals(SHUTTLEFLIGHT_ACTION + "Calcuta", availableActions.get(0).actionPrompt());
    }

    @Test
    public void moveNodeCity_returnsActions() {
        // player is in Paris and can travel to madrid or essen
        player.setCity(paris);
        List<Action> availableActions = MoveNodeCity.returnAvailableActions(player);
        assertEquals(2, availableActions.size());
        assertEquals(DRIVEFERRY, availableActions.get(0).actionsType);
        assertEquals(DRIVEFERRY_ACTION + "Essen", availableActions.get(0).actionPrompt());
        assertEquals(DRIVEFERRY, availableActions.get(1).actionsType);
        assertEquals(DRIVEFERRY_ACTION + "Madrid", availableActions.get(1).actionPrompt());
    }

    @Test
    public void shareKnowledge_player1HasNewYork_returnActions() {
        // All in new york, player1 (newyork, madrid), player2 (tokio, calcuta), player3 (paris, atlanta)
        Player player1 = new Player(newyork);
        player1.setListCard(Arrays.asList(Card.createCityCard(newyork), Card.createCityCard(madrid)));
        Player player2 = new Player(newyork);
        player2.setListCard(Arrays.asList(Card.createCityCard(tokio), Card.createCityCard(calcuta)));
        Player player3 = new Player(newyork);
        player3.setListCard(Arrays.asList(Card.createCityCard(paris), Card.createCityCard(atlanta)));
        List<Player> players = new ArrayList<>();
        players.add(player2);
        players.add(player3);

        List<Action> availableActions = ShareKnowledge.returnAvailableActions(player1, players);
        assertEquals(2, availableActions.size());
        assertEquals(SHAREKNOWLEDGE, availableActions.get(0).actionsType);
        assertEquals(player1, ((ShareKnowledgeAction) availableActions.get(0)).getSender());
        assertEquals(player2, ((ShareKnowledgeAction) availableActions.get(0)).getReceiver());
        assertEquals(SHAREKNOWLEDGE, availableActions.get(1).actionsType);
        assertEquals(player1, ((ShareKnowledgeAction) availableActions.get(1)).getSender());
        assertEquals(player3, ((ShareKnowledgeAction) availableActions.get(1)).getReceiver());
    }

    @Test
    public void shareKnowledge_player3HasNewYork_returnActions() {
        // All in new york, player1 (essen, madrid), player2 (tokio, calcuta), player3 (paris, atlanta)
        Player player1 = new Player(newyork);
        player1.setListCard(Arrays.asList(Card.createCityCard(essen), Card.createCityCard(madrid)));
        Player player2 = new Player(newyork);
        player2.setListCard(Arrays.asList(Card.createCityCard(tokio), Card.createCityCard(calcuta)));
        Player player3 = new Player(newyork);
        player3.setListCard(Arrays.asList(Card.createCityCard(paris), Card.createCityCard(newyork)));
        List<Player> players = new ArrayList<>();
        players.add(player2);
        players.add(player3);

        List<Action> availableActions = ShareKnowledge.returnAvailableActions(player1, players);
        assertEquals(2, availableActions.size());
        assertEquals(SHAREKNOWLEDGE, availableActions.get(0).actionsType);
        assertEquals(player3, ((ShareKnowledgeAction) availableActions.get(0)).getSender());
        assertEquals(player2, ((ShareKnowledgeAction) availableActions.get(0)).getReceiver());
        assertEquals(SHAREKNOWLEDGE, availableActions.get(1).actionsType);
        assertEquals(player3, ((ShareKnowledgeAction) availableActions.get(1)).getSender());
        assertEquals(player1, ((ShareKnowledgeAction) availableActions.get(1)).getReceiver());
    }

    @Test
    public void shareKnowledge_nobodyHasNewYork_returnActions() {
        // All in new york, player1 (essen, madrid), player2 (tokio, calcuta), player3 (paris, newyork)
        Player player1 = new Player(newyork);
        player1.setListCard(Arrays.asList(Card.createCityCard(essen), Card.createCityCard(madrid)));
        Player player2 = new Player(newyork);
        player2.setListCard(Arrays.asList(Card.createCityCard(tokio), Card.createCityCard(calcuta)));
        Player player3 = new Player(newyork);
        player3.setListCard(Arrays.asList(Card.createCityCard(paris), Card.createCityCard(atlanta)));
        List<Player> players = new ArrayList<>();
        players.add(player2);
        players.add(player3);

        List<Action> availableActions = ShareKnowledge.returnAvailableActions(player1, players);
        assertEquals(0, availableActions.size());
    }
}
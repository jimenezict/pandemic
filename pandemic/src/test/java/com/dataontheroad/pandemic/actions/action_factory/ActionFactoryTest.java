package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.default_services.*;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.virus.Virus;
import com.dataontheroad.pandemic.board.virus.VirusType;
import com.dataontheroad.pandemic.board.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.*;
import static com.dataontheroad.pandemic.board.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.CollectionUtils.isEmpty;

class ActionFactoryTest {

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
    void buildResearchCenterAction_isDoable_returnsAction() {
        List<Action> availableActions = BuildResearchCenterDefaultService.returnAvailableActions(player);
        assertEquals(1, availableActions.size());
        assertEquals(BUILDRESEARCHSTATION, availableActions.get(0).actionsType);
        assertEquals(BUILDRESEARCHSTATION_ACTION + "New York", availableActions.get(0).actionPrompt());
    }

    @Test
    void buildResearchCenterAction_isNotDoable_returnsAction() {
        player.getCity().setHasCenter(Boolean.TRUE);
        List<Action> availableActions = BuildResearchCenterDefaultService.returnAvailableActions(player);
        assertTrue(isEmpty(availableActions));
    }

    @Test
    void discoverCure_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards_returnsAction() {
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        player.getCity().setHasCenter(Boolean.TRUE);

        List<Action> availableActions = DiscoverCureDefaultService.returnAvailableActions(player, virusList);
        assertEquals(1, availableActions.size());
        assertEquals(DISCOVERCURE, availableActions.get(0).actionsType);
        assertEquals(DISCOVERCURE_ACTION + "BLUE", availableActions.get(0).actionPrompt());
    }

    @Test
    void discoverCure_cityHasNoResearchCenterCureIsNotDiscoveredHas5BlueCards_returnsAction() {
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        player.getCity().setHasCenter(Boolean.FALSE);

        List<Action> availableActions = DiscoverCureDefaultService.returnAvailableActions(player, virusList);
        assertEquals(0, availableActions.size());
    }

    @Test
    void discoverCure_cityHasResearchCenterCureIsDiscoveredHas5BlueCards_returnsAction() {
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        player.getCity().setHasCenter(Boolean.TRUE);
        virusList.get(0).cureHasBeenDiscovered();

        List<Action> availableActions = DiscoverCureDefaultService.returnAvailableActions(player, virusList);
        assertEquals(0, availableActions.size());
    }

    @Test
    void flyCharter_playerHasCityCardThenCanFlyAnywhere_returnsAction() {
        List<Action> availableActions = FlyCharterDefaultService.returnAvailableActions(player);
        assertEquals(1, availableActions.size());
        assertEquals(FLYCHARTER, availableActions.get(0).actionsType);
        assertEquals(FLYCHARTER_ACTION, availableActions.get(0).actionPrompt());
    }

    @Test
    void flyCharter_playerHasNoCityCardThenCannotFlyAnywhere_returnsAction() {
        player.setCity(madrid);
        List<Action> availableActions = FlyCharterDefaultService.returnAvailableActions(player);
        assertEquals(0, availableActions.size());
    }


    @Test
    void flyDirect_playerHave3ValidCardsToTravel_returnsActions() {
        //player is new york and has card for essen paris madrid
        List<CityCard> listCard = player.getListCard();
        listCard.clear();
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        List<Action> availableActions = FlyDirectCityDefaultService.returnAvailableActions(player);
        assertEquals(3, availableActions.size());
        assertEquals(FLYDIRECT, availableActions.get(0).actionsType);
        assertEquals(FLYDIRECT_ACTION + "Essen", availableActions.get(0).actionPrompt());
    }

    @Test
    void flyDirect_playerHave2ValidCardsToTravel_returnsActions() {
        List<CityCard> listCard = player.getListCard();
        //player is new york and has card for new york paris madrid
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        List<Action> availableActions = FlyDirectCityDefaultService.returnAvailableActions(player);
        assertEquals(2, availableActions.size());
        assertEquals(FLYDIRECT, availableActions.get(0).actionsType);
        assertEquals(FLYDIRECT_ACTION + "Paris", availableActions.get(0).actionPrompt());
    }

    @Test
    void flyShuttle_playerIsNotOnResearchCenter_returnsActions() {
        calcuta.setHasCenter(Boolean.TRUE);
        newyork.setHasCenter(Boolean.FALSE);
        essen.setHasCenter(Boolean.TRUE);
        List<City> citiesWithLabs = Arrays.asList(calcuta, essen);
        List<Action> availableActions = FlyShuttleDefaultService.returnAvailableActions(player, citiesWithLabs);
        assertEquals(0, availableActions.size());
    }

    @Test
    void flyShuttle_playerIsOnResearchCenter_returnsActions() {
        calcuta.setHasCenter(Boolean.TRUE);
        newyork.setHasCenter(Boolean.TRUE);
        essen.setHasCenter(Boolean.TRUE);
        List<City> citiesWithLabs = Arrays.asList(calcuta, essen);
        List<Action> availableActions = FlyShuttleDefaultService.returnAvailableActions(player, citiesWithLabs);
        assertEquals(2, availableActions.size());
        assertEquals(SHUTTLEFLIGHT, availableActions.get(0).actionsType);
        assertEquals(SHUTTLEFLIGHT_ACTION + "Calcuta", availableActions.get(0).actionPrompt());
    }

    @Test
    void moveNodeCity_returnsActions() {
        // player is in Paris and can travel to madrid or essen
        player.setCity(paris);
        List<Action> availableActions = DriveFerryDefaultService.returnAvailableActions(player);
        assertEquals(2, availableActions.size());
        assertEquals(DRIVEFERRY, availableActions.get(0).actionsType);
        assertEquals(DRIVEFERRY_ACTION + "Essen", availableActions.get(0).actionPrompt());
        assertEquals(DRIVEFERRY, availableActions.get(1).actionsType);
        assertEquals(DRIVEFERRY_ACTION + "Madrid", availableActions.get(1).actionPrompt());
    }

    @Test
    void shareKnowledge_player1HasNewYork_returnActions() {
        // All in new york, player1 (newyork, madrid), player2 (tokio, calcuta), player3 (paris, atlanta)
        Player player1 = new Player(newyork);
        player1.setListCard(Arrays.asList(CityCard.createCityCard(newyork), CityCard.createCityCard(madrid)));
        Player player2 = new Player(newyork);
        player2.setListCard(Arrays.asList(CityCard.createCityCard(tokio), CityCard.createCityCard(calcuta)));
        Player player3 = new Player(newyork);
        player3.setListCard(Arrays.asList(CityCard.createCityCard(paris), CityCard.createCityCard(atlanta)));
        List<Player> players = new ArrayList<>();
        players.add(player2);
        players.add(player3);

        List<Action> availableActions = ShareKnowledgeDefaultService.returnAvailableActions(player1, players);
        assertEquals(2, availableActions.size());
        assertEquals(SHAREKNOWLEDGE, availableActions.get(0).actionsType);
        assertEquals(player1, ((ShareKnowledgeAction) availableActions.get(0)).getSender());
        assertEquals(player2, ((ShareKnowledgeAction) availableActions.get(0)).getReceiver());
        assertTrue(availableActions.get(0).actionPrompt().contains(SHAREKNOWLEDGE_ACTION));
        assertEquals(SHAREKNOWLEDGE, availableActions.get(1).actionsType);
        assertEquals(player1, ((ShareKnowledgeAction) availableActions.get(1)).getSender());
        assertEquals(player3, ((ShareKnowledgeAction) availableActions.get(1)).getReceiver());
        assertTrue(availableActions.get(1).actionPrompt().contains(SHAREKNOWLEDGE_ACTION));
    }

    @Test
    void shareKnowledge_player3HasNewYork_returnActions() {
        // All in new york, player1 (essen, madrid), player2 (tokio, calcuta), player3 (paris, atlanta)
        Player player1 = new Player(newyork);
        player1.setListCard(Arrays.asList(CityCard.createCityCard(essen), CityCard.createCityCard(madrid)));
        Player player2 = new Player(newyork);
        player2.setListCard(Arrays.asList(CityCard.createCityCard(tokio), CityCard.createCityCard(calcuta)));
        Player player3 = new Player(newyork);
        player3.setListCard(Arrays.asList(CityCard.createCityCard(paris), CityCard.createCityCard(newyork)));
        List<Player> players = new ArrayList<>();
        players.add(player2);
        players.add(player3);

        List<Action> availableActions = ShareKnowledgeDefaultService.returnAvailableActions(player1, players);
        assertEquals(2, availableActions.size());
        assertEquals(SHAREKNOWLEDGE, availableActions.get(0).actionsType);
        assertEquals(player3, ((ShareKnowledgeAction) availableActions.get(0)).getSender());
        assertEquals(player2, ((ShareKnowledgeAction) availableActions.get(0)).getReceiver());
        assertEquals(SHAREKNOWLEDGE, availableActions.get(1).actionsType);
        assertEquals(player3, ((ShareKnowledgeAction) availableActions.get(1)).getSender());
        assertEquals(player1, ((ShareKnowledgeAction) availableActions.get(1)).getReceiver());
    }

    @Test
    void shareKnowledge_nobodyHasNewYork_returnActions() {
        // All in new york, player1 (essen, madrid), player2 (tokio, calcuta), player3 (paris, newyork)
        Player player1 = new Player(newyork);
        player1.setListCard(Arrays.asList(CityCard.createCityCard(essen), CityCard.createCityCard(madrid)));
        Player player2 = new Player(newyork);
        player2.setListCard(Arrays.asList(CityCard.createCityCard(tokio), CityCard.createCityCard(calcuta)));
        Player player3 = new Player(newyork);
        player3.setListCard(Arrays.asList(CityCard.createCityCard(paris), CityCard.createCityCard(atlanta)));
        List<Player> players = new ArrayList<>();
        players.add(player2);
        players.add(player3);

        List<Action> availableActions = ShareKnowledgeDefaultService.returnAvailableActions(player1, players);
        assertEquals(0, availableActions.size());
    }
}
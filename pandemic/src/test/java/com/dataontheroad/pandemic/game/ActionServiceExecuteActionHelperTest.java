package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.action_factory.*;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class ActionServiceExecuteActionHelperTest {

    // All the test in this class use a player with regular services, with no customization by role.

    Player player;
    final List<City> emptyNodeCityConnection = new ArrayList<>();
    private final City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private final City calcuta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private final City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private final City madrid = new City("Madrid", VirusType.BLUE, emptyNodeCityConnection);
    private final City paris = new City("Paris", VirusType.BLUE, Arrays.asList(essen, madrid));
    private final City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private final City atlanta = new City("Atlanta", VirusType.BLUE, emptyNodeCityConnection);

    private Virus blueVirus = new Virus(VirusType.BLUE);
    private final Virus blackVirus = new Virus(VirusType.BLACK);
    private final Virus redVirus = new Virus(VirusType.RED);
    private final Virus yellowVirus = new Virus(VirusType.YELLOW);
    List<Virus> virusList;


    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setCity(newyork);
        blueVirus = new Virus(VirusType.BLUE);
        player.getListCard().add(createCityCard(newyork));
        virusList = Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
        calcuta.setHasCenter(FALSE);
        newyork.setHasCenter(FALSE);
        essen.setHasCenter(FALSE);
    }

    @Test
    void executeAction_buildResearchCenter_correct() throws ActionException {
        //player is in New York
        //New York do NOT have research center
        //Player has DefaultService for BuildResearchCenter
        //Player has the card of New York
        BuildResearchCenterAction action = new BuildResearchCenterAction(player);
        action.execute();
        assertTrue(player.getCity().getHasCenter());
    }

    @Test
    void executeAction_buildResearchCenter_withoutCard() {
        //player is in New York
        //New York has NOT research center
        //Player has DefaultService for BuildResearchCenter
        //Player has not the card of New York
        player.getListCard().remove(0);
        BuildResearchCenterAction action = new BuildResearchCenterAction(player);
        assertThrows(ActionException.class, () -> action.execute());
        assertFalse(player.getCity().getHasCenter());
    }

    @Test
    void executeAction_buildResearchCenter_hasResearchCenter() {
        //player is in New York
        //New York has a research center
        //Player has DefaultService for BuildResearchCenter
        //Player has the card of New York
        newyork.setHasCenter(TRUE);
        BuildResearchCenterAction action = new BuildResearchCenterAction(player);
        assertThrows(ActionException.class, () -> action.execute());
        assertTrue(player.getCity().getHasCenter());
    }

    @Test
    void executeAction_DiscoverCure_correct() throws ActionException {
        //Player is in New York
        //New York has a research center
        //Player has 5 blue cards
        //Cure not discovered yet

        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        newyork.setHasCenter(TRUE);
        DiscoverCureAction action = new DiscoverCureAction(player, blueVirus);
        action.execute();
        assertTrue(blueVirus.getCureDiscovered());
    }

    @Test
    void executeAction_DiscoverCure_cureAlreadyDiscovered() {
        //Player is in New York
        //New York has a research center
        //Player has 5 blue cards
        //Cure discovered yet

        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        blueVirus.cureHasBeenDiscovered();

        newyork.setHasCenter(TRUE);
        DiscoverCureAction action = new DiscoverCureAction(player, blueVirus);
        assertThrows(ActionException.class, () -> action.execute());
        assertTrue(blueVirus.getCureDiscovered());
    }

    @Test
    void executeAction_DiscoverCure_cityHasNotResearchCenter() {
        //Player is in New York
        //New York has not a research center
        //Player has 5 blue cards
        //Cure not discovered yet

        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        newyork.setHasCenter(FALSE);
        DiscoverCureAction action = new DiscoverCureAction(player, blueVirus);
        assertThrows(ActionException.class, () -> action.execute());
        assertFalse(blueVirus.getCureDiscovered());
    }

    @Test
    void executeAction_DiscoverCure_playerHasNotEnoughCards() {
        //Player is in New York
        //New York has a research center
        //Player has 2 blue cards
        //Cure not discovered yet

        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));

        newyork.setHasCenter(TRUE);
        DiscoverCureAction action = new DiscoverCureAction(player, blueVirus);
        assertThrows(ActionException.class, () -> action.execute());
        assertFalse(blueVirus.getCureDiscovered());
    }

    @Test
    void executeAction_DriveFerryAction_correct() throws ActionException {
        //Player is in New York
        //Player goes to Atlanta
        //There is connection between cities

        newyork.getNodeCityConnection().add(atlanta);
        atlanta.getNodeCityConnection().add(newyork);

        DriveFerryAction action = new DriveFerryAction(player, atlanta);
        action.execute();
        assertEquals(atlanta, player.getCity());

        action = new DriveFerryAction(player, newyork);
        action.execute();
        assertEquals(newyork, player.getCity());
    }

    @Test
    void executeAction_DriveFerryAction_noConnectionBetweenCities() {
        //Player is in New York
        //Player goes to Atlanta
        //There is connection between cities

        newyork.getNodeCityConnection().add(atlanta);
        atlanta.getNodeCityConnection().add(newyork);

        DriveFerryAction action = new DriveFerryAction(player, tokio);
        assertThrows(ActionException.class, () -> action.execute());
    }

    @Test
    void executeAction_FlyCharterAction_correct() throws ActionException {
        //Player is in New York
        //Player goes to Essen
        //Player has New York card

        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        FlyCharterAction action = new FlyCharterAction(player);
        action.setDestination(essen);

        action.execute();
        assertEquals(essen, player.getCity());
    }

    @Test
    void executeAction_FlyCharterAction_hasNotEssenCard() {
        //Player is in Tokio
        //Player goes to Essen
        //Player has not Tokio card

        player.setCity(tokio);
        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        FlyCharterAction action = new FlyCharterAction(player);
        action.setDestination(essen);

        assertThrows(ActionException.class, () -> action.execute());
    }

    @Test
    void executeAction_FlyDirectAction_correct() throws ActionException {
        //Player is in New York
        //Player goes to Essen
        //Player has Essen card

        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        FlyDirectAction action = new FlyDirectAction(player, essen);

        action.execute();
        assertEquals(essen, player.getCity());
    }

    @Test
    void executeAction_FlyDirectAction_doNotHasDestinationCard() {
        //Player is in New York
        //Player goes to Tokio
        //Player has Essen card

        List<BaseCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        FlyDirectAction action = new FlyDirectAction(player, tokio);

        assertThrows(ActionException.class, () -> action.execute());
    }

    @Test
    void executeAction_FlyShuttleAction_correct() throws ActionException {
        //Player is in New York
        //New York has research center
        //Player fly to essen and has research center
        newyork.setHasCenter(TRUE);
        essen.setHasCenter(TRUE);
        FlyShuttleAction action = new FlyShuttleAction(player, essen);

        action.execute();
        assertEquals(essen, player.getCity());
    }

    @Test
    void executeAction_FlyShuttleAction_originWithoutResearchCenter() {
        //Player is in New York
        //New York has not research center
        //Player fly to essen and has research center
        newyork.setHasCenter(FALSE);
        essen.setHasCenter(TRUE);
        FlyShuttleAction action = new FlyShuttleAction(player, essen);

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> action.execute());
        assertTrue(exception.getMessage().contains(SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION));
    }

    @Test
    void executeAction_FlyShuttleAction_destinationWithoutResearchCenter() {
        //Player is in New York
        //New York has not research center
        //Player fly to essen and has research center
        newyork.setHasCenter(TRUE);
        essen.setHasCenter(FALSE);
        FlyShuttleAction action = new FlyShuttleAction(player, essen);

        assertThrows(ActionException.class, () -> action.execute());
    }


    @Test
    void executeAction_ShareKnowledge_correct() throws ActionException {
        //Sender (player) is in New York
        //Receiver is in New York
        //Sender has New York card
        //Receiver has 0 card
        Player receiver = new Player();
        receiver.setCity(newyork);

        ShareKnowledgeAction action = new ShareKnowledgeAction(player, receiver,(CityCard) player.getListCard().get(0));

        action.execute();
        assertTrue(player.getListCard().isEmpty());
        assertEquals(newyork.getName(), ((CityCard) receiver.getListCard().get(0)).getCity().getName());
    }

    @Test
    void executeAction_ShareKnowledge_differentCity() {
        //Sender (player) is in New York
        //Receiver is in Tokio
        //Sender has New York card
        //Receiver has 0 card
        Player receiver = new Player();
        receiver.setCity(tokio);

        ShareKnowledgeAction action = new ShareKnowledgeAction(player, receiver,(CityCard) player.getListCard().get(0));

        assertThrows(ActionException.class, () -> action.execute());
    }

    @Test
    void executeAction_ShareKnowledge_incorrectCardToChange() {
        //Sender (player) is in New York
        //Receiver is in New York
        //Sender has Tokio card
        //Receiver has 0 card
        Player receiver = new Player();
        receiver.setCity(newyork);
        player.getListCard().remove(0);
        player.getListCard().add(createCityCard(tokio));

        ShareKnowledgeAction action = new ShareKnowledgeAction(player, receiver,(CityCard) player.getListCard().get(0));

        assertThrows(ActionException.class, () -> action.execute());
    }
}
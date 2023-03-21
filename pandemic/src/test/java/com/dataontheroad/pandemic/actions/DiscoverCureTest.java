package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class DiscoverCureTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calcuta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City madrid = new City("Madrid", VirusType.BLUE, emptyNodeCityConnection);
    private City paris = new City("Paris", VirusType.BLUE, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private City atlanta = new City("Atlanta", VirusType.BLUE, emptyNodeCityConnection);

    private Virus virusNotDiscovered = new Virus(VirusType.BLUE);
    private Virus virusDiscovered = new Virus(VirusType.BLACK);

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(newyork);
        newyork.setHasCenter(Boolean.TRUE);
        calcuta.setHasCenter(Boolean.FALSE);

        virusDiscovered = new Virus(VirusType.BLACK);
        virusDiscovered.cureHasBeenDiscovered();
        virusNotDiscovered = new Virus(VirusType.BLUE);
    }

    @Test
    public void isDoable_cityHasNoResearchCenter_thenFalse() {
        player.setCity(calcuta);
        assertFalse(DiscoverCure.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsDiscovered_thenFalse() {
        assertFalse(DiscoverCure.isDoable(player, virusDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHasNoCards_thenFalse() {
        assertFalse(DiscoverCure.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHas2BlueCards_thenFalse() {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        assertFalse(DiscoverCure.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards_thenTrue() {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(newyork));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        assertTrue(DiscoverCure.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards2Others_thenTrue() {
         List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(newyork));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        listCard.add(createCityCard(tokio));
        listCard.add(createCityCard(calcuta));

        assertTrue(DiscoverCure.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void doAction_originCityHasNoResearchCenter_throwException() {
        player.setCity(calcuta);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> DiscoverCure.doAction(player, virusNotDiscovered));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DISCOVERCURE.label));
        assertTrue(actualMessage.contains("The city do not have a research station"));
    }

    @Test
    public void doAction_originCityHasResearchCenterVirusDiscovered_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> DiscoverCure.doAction(player, virusDiscovered));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DISCOVERCURE.label));
        assertTrue(actualMessage.contains("The cure has been already discovered"));
    }

    @Test
    public void doAction_cityHasResearchCenterCureIsNotDiscoveredHas2BlueCards_throwException() {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> DiscoverCure.doAction(player, virusNotDiscovered));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DISCOVERCURE.label));
        assertTrue(actualMessage.contains("The player do not have enough cars"));
    }

    @Test
    public void doAction_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards_virusIsDiscovered() throws ActionException {
        List<Card> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(newyork));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        DiscoverCure.doAction(player, virusNotDiscovered);
        assertTrue(virusDiscovered.getCureDiscovered());
    }
}
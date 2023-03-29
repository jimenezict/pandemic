package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.*;
import com.dataontheroad.pandemic.board.model.enums.VirusType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.board.cards.model.CityCard.createCityCard;
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
        assertFalse(DiscoverCureDefaultService.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsDiscovered_thenFalse() {
        assertFalse(DiscoverCureDefaultService.isDoable(player, virusDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHasNoCards_thenFalse() {
        assertFalse(DiscoverCureDefaultService.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHas2BlueCards_thenFalse() {
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        assertFalse(DiscoverCureDefaultService.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards_thenTrue() {
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(newyork));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        assertTrue(DiscoverCureDefaultService.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void isDoable_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards2Others_thenTrue() {
         List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(newyork));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));
        listCard.add(createCityCard(tokio));
        listCard.add(createCityCard(calcuta));

        assertTrue(DiscoverCureDefaultService.isDoable(player, virusNotDiscovered));
    }

    @Test
    public void doAction_originCityHasNoResearchCenter_throwException() {
        player.setCity(calcuta);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> DiscoverCureDefaultService.doAction(player, virusNotDiscovered));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DISCOVERCURE.label));
        assertTrue(actualMessage.contains(DISCOVERCURE_ERROR_NO_RESEARCH_STATION));
    }

    @Test
    public void doAction_originCityHasResearchCenterVirusDiscovered_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> DiscoverCureDefaultService.doAction(player, virusDiscovered));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DISCOVERCURE.label));
        assertTrue(actualMessage.contains(DISCOVERCURE_ERROR_CURE_DISCOVERED));
    }

    @Test
    public void doAction_cityHasResearchCenterCureIsNotDiscoveredHas2BlueCards_throwException() {
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> DiscoverCureDefaultService.doAction(player, virusNotDiscovered));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DISCOVERCURE.label));
        assertTrue(actualMessage.contains(DISCOVERCURE_ERROR_NO_CARD));
    }

    @Test
    public void doAction_cityHasResearchCenterCureIsNotDiscoveredHas5BlueCards_virusIsDiscovered() throws ActionException {
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(paris));
        listCard.add(createCityCard(madrid));
        listCard.add(createCityCard(newyork));
        listCard.add(createCityCard(essen));
        listCard.add(createCityCard(atlanta));

        DiscoverCureDefaultService.doAction(player, virusNotDiscovered);
        assertTrue(virusDiscovered.getCureDiscovered());
    }
}
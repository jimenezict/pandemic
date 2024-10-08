package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_CENTER_CREATED;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class BuildResearchCenterDefaultServiceTest {

    Player player;

    final List<City> emptyNodeCityConnection = new ArrayList<>();

    private final City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private final City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private final City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private final City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private final City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);

    Action action;

    private final BuildResearchCenterDefaultService buildResearchCenterDefaultService= new BuildResearchCenterDefaultService();

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(newyork);

        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        player.setListCard(cardList);
    }
    @Test
    void isDoable_cityHasAlreadyResearchCenter_thenFalse() {
        newyork.setHasCenter(TRUE);
        assertFalse(buildResearchCenterDefaultService.isDoable(player));
    }

    @Test
    void isDoable_cityHasNoResearchCenter_HasNoCardForHisPosition_thenFalse() {
        player.setCity(tokio);
        assertFalse(buildResearchCenterDefaultService.isDoable(player));
    }

    @Test
    void isDoable_cityHasNoResearchCenter_HasCardForHisPosition_thenFalse() {
        assertTrue(buildResearchCenterDefaultService.isDoable(player));
    }

    @Test
    void doAction_cityHasAlreadyResearchCenter_throwException() {
        newyork.setHasCenter(TRUE);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> buildResearchCenterDefaultService.doAction(player));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.BUILDRESEARCHSTATION.label));
        assertTrue(actualMessage.contains(BUILDRESEARCHSTATION_ERROR_CENTER_CREATED));
    }

    @Test
    void doAction_cityHasNoResearchCenterAndPlayerIsNotOnSite_throwException() {
        player.setCity(tokio);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> buildResearchCenterDefaultService.doAction(player));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.BUILDRESEARCHSTATION.label));
        assertTrue(actualMessage.contains(BUILDRESEARCHSTATION_ERROR_NO_CARD));
    }

    @Test
    void doAction_cityHasNoResearchCenter_createCenterAndRemoveCard() throws ActionException {
        buildResearchCenterDefaultService.doAction(player);
        assertTrue(newyork.getHasCenter());
        assertFalse(player.getListCard().contains(createCityCard(newyork)));
    }
}
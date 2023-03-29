package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.board.model.enums.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_CENTER_CREATED;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.board.cards.model.CityCard.createCityCard;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class BuildResearchCenterDefaultServiceTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);

    Action action;

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(newyork);

        List<CityCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        player.setListCard(cardList);
    }
    @Test
    public void isDoable_cityHasAlreadyResearchCenter_thenFalse() {
        newyork.setHasCenter(TRUE);
        assertFalse(BuildResearchCenterDefaultService.isDoable(player));
    }

    @Test
    public void isDoable_cityHasNoResearchCenter_HasNoCardForHisPosition_thenFalse() {
        player.setCity(tokio);
        assertFalse(BuildResearchCenterDefaultService.isDoable(player));
    }

    @Test
    public void isDoable_cityHasNoResearchCenter_HasCardForHisPosition_thenFalse() {
        assertTrue(BuildResearchCenterDefaultService.isDoable(player));
    }

    @Test
    public void doAction_cityHasAlreadyResearchCenter_throwException() {
        newyork.setHasCenter(TRUE);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> BuildResearchCenterDefaultService.doAction(player));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.BUILDRESEARCHSTATION.label));
        assertTrue(actualMessage.contains(BUILDRESEARCHSTATION_ERROR_CENTER_CREATED));
    }

    @Test
    public void doAction_cityHasNoResearchCenterAndPlayerIsNotOnSite_throwException() {
        player.setCity(tokio);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> BuildResearchCenterDefaultService.doAction(player));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.BUILDRESEARCHSTATION.label));
        assertTrue(actualMessage.contains(BUILDRESEARCHSTATION_ERROR_NO_CARD));
    }

    @Test
    public void doAction_cityHasNoResearchCenter_createCenterAndRemoveCard() throws ActionException {
        BuildResearchCenterDefaultService.doAction(player);
        assertTrue(newyork.getHasCenter());
        assertFalse(player.getListCard().contains(createCityCard(newyork)));
    }
}
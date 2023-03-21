package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.actions.services.BuildResearchCenter;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class BuildResearchCenterTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(newyork);

        List<Card> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        player.setListCard(cardList);
    }
    @Test
    public void isDoable_cityHasAlreadyResearchCenter_thenFalse() {
        newyork.setHasCenter(TRUE);
        assertFalse(BuildResearchCenter.isDoable(player));
    }

    @Test
    public void isDoable_cityHasNoResearchCenter_HasNoCardForHisPosition_thenFalse() {
        player.setCity(tokio);
        assertFalse(BuildResearchCenter.isDoable(player));
    }

    @Test
    public void isDoable_cityHasNoResearchCenter_HasCardForHisPosition_thenFalse() {
        assertTrue(BuildResearchCenter.isDoable(player));
    }

    @Test
    public void doAction_cityHasAlreadyResearchCenter_throwException() {
        newyork.setHasCenter(TRUE);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> BuildResearchCenter.doAction(player));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.BUILDRESEARCHCENTER.label));
        assertTrue(actualMessage.contains("Center already created"));
    }

    @Test
    public void doAction_cityHasNoResearchCenterAndPlayerIsNotOnSite_throwException() {
        player.setCity(tokio);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> BuildResearchCenter.doAction(player));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.BUILDRESEARCHCENTER.label));
        assertTrue(actualMessage.contains("Player has no card for that city"));
    }

    @Test
    public void doAction_cityHasNoResearchCenter_createCenterAndRemoveCard() throws ActionException {
        BuildResearchCenter.doAction(player);
        assertTrue(newyork.getHasCenter());
        assertFalse(player.getListCard().contains(createCityCard(newyork)));
    }
}
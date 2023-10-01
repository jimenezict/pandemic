package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_CENTER_CREATED;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class OperationsBuildResearchCenterServiceTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);

    Action action;

    OperationsBuildResearchCenterService operationsBuildResearchCenterService = new OperationsBuildResearchCenterService();
    @BeforeEach
    public void setPlayer() {
        player = new OperationsPlayer();
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
        assertFalse(operationsBuildResearchCenterService.isDoable(player));
    }

    @Test
    void isDoable_cityHasNoResearchCenter_HasNoCardForHisPosition_thenFalse() {
        player.setCity(tokio);
        tokio.setHasCenter(FALSE);
        assertTrue(operationsBuildResearchCenterService.isDoable(player));
    }

    @Test
    void isDoable_cityHasNoResearchCenter_HasCardForHisPosition_thenFalse() {
        assertTrue(operationsBuildResearchCenterService.isDoable(player));
    }

    @Test
    void doAction_cityHasAlreadyResearchCenter_throwException() {
        newyork.setHasCenter(TRUE);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> operationsBuildResearchCenterService.doAction(player));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.BUILDRESEARCHSTATION.label));
        assertTrue(actualMessage.contains(BUILDRESEARCHSTATION_ERROR_CENTER_CREATED));
    }

    @Test
    void doAction_cityHasNoResearchCenter_createCenterAndRemoveCard() throws ActionException {
        operationsBuildResearchCenterService.doAction(player);
        assertTrue(newyork.getHasCenter());
        assertTrue(player.getListCard().contains(createCityCard(newyork)));
    }
}
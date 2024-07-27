package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.OPERATION_FLY;
import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.model.city.CityEnum.ATLANTA;
import static com.dataontheroad.pandemic.model.city.CityEnum.LAGOS;
import static org.junit.jupiter.api.Assertions.*;

class OperationsFlyFromReserachServiceTest {

    OperationsFlyFromReserachService operationsFlyFromReserachService = OperationsFlyFromReserachService.getInstance();

    OperationsPlayer operationsPlayer;
    private City lagos = new City(LAGOS.cityName, VirusType.YELLOW);
    private City atlanta = new City(ATLANTA.cityName, VirusType.BLACK);
    private City newyork = new City("New York", VirusType.BLUE);
    private City calculta = new City("Calcuta", VirusType.BLACK);
    private City essen = new City("Essen", VirusType.BLUE);
    private City lima = new City("Lima", VirusType.YELLOW);

    @BeforeEach
    public void setUp() {
        operationsPlayer = new OperationsPlayer();
        atlanta.setHasCenter(Boolean.TRUE);
        lagos.setHasCenter(Boolean.FALSE);
        operationsPlayer.setCity(atlanta);
        operationsPlayer.resetActionAvailable();
        operationsPlayer.setListCard(new ArrayList<>());
        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        operationsPlayer.setListCard(cardList);
    }

    @Test
    void isDoable_false_cityWithoutResearchCenter() {
        operationsPlayer.setCity(lagos);
        assertFalse(operationsFlyFromReserachService.isDoable(operationsPlayer));
    }

    @Test
    void isDoable_false_playerHasNoCards() {
        operationsPlayer.setListCard(new ArrayList<>());

        assertTrue(operationsPlayer.canPlayerExecuteActionThisTurn());
        assertFalse(operationsFlyFromReserachService.isDoable(operationsPlayer));
    }

    @Test
    void isDoable_false_playerAlreadyPlayed() {
        operationsPlayer.actionHasBeenExecuted();

        assertFalse(operationsPlayer.canPlayerExecuteActionThisTurn());
        assertFalse(operationsFlyFromReserachService.isDoable(operationsPlayer));
    }

    @Test
    void isDoable_true_allConditionsAcomplished() {
        assertTrue(operationsFlyFromReserachService.isDoable(operationsPlayer));
    }

    @Test
    void availableAction_isEmpty_cityWithoutResearchCenter() {
        operationsPlayer.setCity(lagos);
        assertTrue(operationsFlyFromReserachService.returnAvailableActions(operationsPlayer).isEmpty());
    }

    @Test
    void availableAction_isEmpty_playerHasNoCards() {
        operationsPlayer.setListCard(new ArrayList<>());

        assertTrue(operationsPlayer.canPlayerExecuteActionThisTurn());
        assertTrue(operationsFlyFromReserachService.returnAvailableActions(operationsPlayer).isEmpty());
    }

    @Test
    void availableAction_isEmpty_playerAlreadyPlayed() {
        operationsPlayer.actionHasBeenExecuted();

        assertFalse(operationsPlayer.canPlayerExecuteActionThisTurn());
        assertTrue(operationsFlyFromReserachService.returnAvailableActions(operationsPlayer).isEmpty());
    }

    @Test
    void availableAction_allConditionsAcomplished() {
        List<Action> list = operationsFlyFromReserachService.returnAvailableActions(operationsPlayer);
        assertEquals(1, list.size());
        assertEquals(OPERATION_FLY, list.get(0).getActionsType());
        assertEquals(operationsPlayer, list.get(0).getPlayer());
    }

    @Test
    void doAction_exception_cityWithoutResearchCenter() {
        operationsPlayer.setCity(lagos);

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> operationsFlyFromReserachService.doAction(operationsPlayer, atlanta, operationsPlayer.getListCard().get(0)));
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(OPERATIONFLY_ERROR_CITYWITHOUTRESEARCHCENTER));
        assertTrue(actualMessage.contains(ActionsType.OPERATION_FLY.label));
    }

    @Test
    void doAction_exception_playerHasNoCards() {
        operationsPlayer.setListCard(new ArrayList<>());

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> operationsFlyFromReserachService.doAction(operationsPlayer, atlanta, null));
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(OPERATIONFLY_ERROR_PLAYERHASNOCARD));
        assertTrue(actualMessage.contains(ActionsType.OPERATION_FLY.label));
    }

    @Test
    void doAction_exception_playerAlreadyFlyThisTurn() {
        operationsPlayer.actionHasBeenExecuted();
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> operationsFlyFromReserachService.doAction(operationsPlayer, atlanta, operationsPlayer.getListCard().get(0)));
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(OPERATIONFLY_ERROR_PLAYERHASALREADYFLYTHISTURN));
        assertTrue(actualMessage.contains(ActionsType.OPERATION_FLY.label));

    }

    @Test
    void doAction_actionIsDoable() throws ActionException {
        operationsFlyFromReserachService.doAction(operationsPlayer, lagos, operationsPlayer.getListCard().get(0));
        assertEquals(lagos, operationsPlayer.getCity());
        assertEquals(3, operationsPlayer.getListCard().size());
        assertFalse(operationsPlayer.canPlayerExecuteActionThisTurn());
    }
}
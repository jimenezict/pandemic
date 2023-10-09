package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.FlyFromResearchCenterAnywhereAction;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.TakeDiscardEventCardAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.GovernmentGrantEventCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.*;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.OPERATION_FLY;
import static com.dataontheroad.pandemic.actions.ActionsType.TAKEDISCARDEVENTCARD;
import static com.dataontheroad.pandemic.constants.LiteralsCard.SPECIAL_EVENT_GOVERNMENT_GRANT_NAME;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.OPERATIONS_NAME;
import static com.dataontheroad.pandemic.game.ActionServiceHelper.getListOfSpecialActions;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.model.city.CityEnum.MADRID;
import static com.dataontheroad.pandemic.model.city.CityFactory.createCityList;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GetListOfActionsSpecialActionTest {

    List<City> cityList, citiesWithResearchCenter;
    Player player;
    List<Virus> virusList;


    private Virus blueVirus = new Virus(VirusType.BLUE);
    private Virus blackVirus = new Virus(VirusType.BLACK);
    private Virus redVirus = new Virus(VirusType.RED);
    private Virus yellowVirus = new Virus(VirusType.YELLOW);

    @BeforeEach
    public void setUp() {
        cityList = createCityList();
        player = new Player(cityList.get(1));
        virusList = Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
        citiesWithResearchCenter = cityList.subList(1,2);
    }

    @Test
    void getListOfActions_regularPlayerReturnsEmptyString() {
        List<Action> actions = getListOfSpecialActions(player, new ArrayList(), new ArrayList<>());
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_MedicReturnsEmptyString() {
        List<Action> actions = getListOfSpecialActions(new MedicPlayer(), new ArrayList(), new ArrayList<>());
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_QuarentineReturnsEmptyString() {
        List<Action> actions = getListOfSpecialActions(new QuarantinePlayer(), new ArrayList(), new ArrayList<>());
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_ResearchReturnsEmptyString() {
        List<Action> actions = getListOfSpecialActions(new ResearchPlayer(), new ArrayList(), new ArrayList<>());
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_ScientistsReturnsEmptyString() {
        List<Action> actions = getListOfSpecialActions(new ScientistPlayer(), new ArrayList(), new ArrayList<>());
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_OperationsIsOnAcityWithoutResearchCenter() {
        // Operation is in a city without center, so can not use the OPERATIONFLY action
        OperationsPlayer operationsPlayer = new OperationsPlayer();
        operationsPlayer.setListCard(Arrays.asList(createCityCard(cityList.get(24))));
        operationsPlayer.setCity(cityList.get(10));
        List<Action> actions = getListOfSpecialActions(operationsPlayer, new ArrayList(), new ArrayList<>());
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_OperationsHasNoCards() {
        // Operation is on a city without research center, but has no cards so can not use the OPERATIONFLY action
        OperationsPlayer operationsPlayer = new OperationsPlayer();
        operationsPlayer.setCity(cityList.get(1));
        List<Action> actions = getListOfSpecialActions(operationsPlayer, new ArrayList(), new ArrayList<>());
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_OperationExecutesActionAndValidateCanNotExecuteTwice() throws ActionException {

        OperationsPlayer operationsPlayer = new OperationsPlayer();
        operationsPlayer.setCity(cityList.get(1));
        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(cityList.get(24)));
        cardList.add(createCityCard(cityList.get(25)));
        operationsPlayer.setListCard(cardList);

        // Operation is on a city without research center, has cards, and not yet executed the action this turn
        List<Action> actions = getListOfSpecialActions(operationsPlayer, new ArrayList(), new ArrayList());
        assertEquals(1, actions.size());

        // Executes the only available action
        FlyFromResearchCenterAnywhereAction operationFly = (FlyFromResearchCenterAnywhereAction) actions.get(0);
        assertEquals(OPERATION_FLY, operationFly.getActionsType());
        assertEquals(OPERATIONS_NAME, operationFly.getPlayer().getName());

        // Set up missing fields
        City madrid = new City(MADRID.name(), VirusType.BLUE);
        operationFly.setDestination(madrid);
        operationFly.setDiscardCard(cardList.get(0));
        operationFly.execute();

        assertEquals(madrid, operationsPlayer.getCity());

        madrid.setHasCenter(TRUE);
        actions = getListOfSpecialActions(operationsPlayer, new ArrayList(), new ArrayList());
        assertEquals(0, actions.size());

    }

    @Test
    void getListOfActions_ContingencyHaveDiscardedCardsButNotEvents() {
        ContingencyPlayer contingencyPlayer = new ContingencyPlayer();
        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(cityList.get(24)));
        cardList.add(createCityCard(cityList.get(25)));

        List<Action> actions = getListOfSpecialActions(contingencyPlayer, new ArrayList(), cardList);
        assertEquals(0, actions.size());
    }

    @Test
    void getListOfActions_ContingencyHaveEventsOnDiscardedCards() throws ActionException {
        ContingencyPlayer contingencyPlayer = new ContingencyPlayer();
        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(cityList.get(24)));
        cardList.add(createCityCard(cityList.get(25)));
        cardList.add(new GovernmentGrantEventCard());
        cardList.add(new GovernmentGrantEventCard());

        List<Action> actions = getListOfSpecialActions(contingencyPlayer, new ArrayList(), cardList);
        assertEquals(2, actions.size());

        TakeDiscardEventCardAction action = (TakeDiscardEventCardAction) actions.get(0);
        action.execute();

        assertEquals(3, cardList.size());
        assertEquals(SPECIAL_EVENT_GOVERNMENT_GRANT_NAME, contingencyPlayer.getExtraEventCard().getEventName());

        actions = getListOfSpecialActions(contingencyPlayer, new ArrayList(), cardList);
        assertEquals(0, actions.size());
    }

}
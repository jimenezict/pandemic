package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.MOVEPAWNTOPAWN;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;
import static com.dataontheroad.pandemic.model.city.CityEnum.*;
import static com.dataontheroad.pandemic.model.city.CityFactory.createCityList;
import static org.junit.jupiter.api.Assertions.*;

class DispatcherMovePawnToPawnServiceTest {

    List<City> cityList = createCityList();
    List<Player> playerList;
    MedicPlayer medic;
    ContingencyPlayer contingency;
    DispatcherPlayer dispatcher;
    ScientistPlayer scientist;

    DispatcherMovePawnToPawnService dispatcherMovePawnToPawnService;

    @BeforeEach
    public void setUp() {
        medic = new MedicPlayer();
        medic.setCity(getCityFromBoardList(new City(ATLANTA.cityName, ATLANTA.virusType)));
        scientist = new ScientistPlayer();
        scientist.setCity(getCityFromBoardList(new City(ATLANTA.cityName, ATLANTA.virusType)));
        dispatcher = new DispatcherPlayer();
        dispatcher.setCity(getCityFromBoardList(new City(PARIS.cityName, PARIS.virusType)));
        contingency = new ContingencyPlayer();
        contingency.setCity(getCityFromBoardList(new City(CHENNAI.cityName, CHENNAI.virusType)));

        playerList = new ArrayList<>();

        playerList.add(medic);
        playerList.add(scientist);
        playerList.add(dispatcher);
        playerList.add(contingency);

        dispatcherMovePawnToPawnService = DispatcherMovePawnToPawnService.getInstance();
    }

    private City getCityFromBoardList(City inputCity) {
        return cityList.stream().filter(city -> city.equals(inputCity)).findFirst().orElse(null);
    }

    @Test
    void isDoable_BothPlayersOnSameCity() {
        assertFalse(DispatcherMovePawnToPawnService.isDoable(playerList, medic, new City(ATLANTA.cityName, ATLANTA.virusType)));
    }

    @Test
    void isDoable_MedicCanTravelToParis() {
        assertTrue(DispatcherMovePawnToPawnService.isDoable(playerList, medic, new City(PARIS.cityName, PARIS.virusType)));
    }

    @Test
    void returnAvailableActions_DestinationHasNoPawn() {
        List<Action> availableActions = DispatcherMovePawnToPawnService.returnAvailableActions(playerList);
        assertEquals(10, availableActions.stream().filter(action -> MOVEPAWNTOPAWN.equals(action.getActionsType())).count());
        assertEquals(2, availableActions.stream().filter(action -> MEDIC_NAME.equals(action.getPlayer().getName())).count());
        assertEquals(3, availableActions.stream().filter(action -> CONTINGENCY_NAME.equals(action.getPlayer().getName())).count());
        assertEquals(3, availableActions.stream().filter(action -> DISPATCHER_NAME.equals(action.getPlayer().getName())).count());
        assertEquals(2, availableActions.stream().filter(action -> SCIENTIST_NAME.equals(action.getPlayer().getName())).count());
    }

    @Test
    void doAction_successcase() throws ActionException {
        List<Action> availableActions = DispatcherMovePawnToPawnService.returnAvailableActions(playerList);
        availableActions.get(0).execute();
        assertEquals(PARIS.cityName, playerList.get(0).getCity().getName());
    }


}
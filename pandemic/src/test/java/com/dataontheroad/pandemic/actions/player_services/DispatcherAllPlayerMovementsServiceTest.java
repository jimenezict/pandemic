package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.DispatcherPlayer;
import com.dataontheroad.pandemic.model.player.MedicPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.QuarantinePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.DRIVEFERRYDISPATCHER;
import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.MEDIC_NAME;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.QUARANTINE_NAME;
import static com.dataontheroad.pandemic.model.city.CityEnum.*;
import static com.dataontheroad.pandemic.model.city.CityFactory.createCityList;
import static org.junit.jupiter.api.Assertions.*;

class DispatcherAllPlayerMovementsServiceTest {

    final List<City> cityList = createCityList();
    Player player;

    final DispatcherAllPlayerMovementsService dispatcherAllPlayerMovementsService = DispatcherAllPlayerMovementsService.getInstance();
    private City getCityFromBoardList(City inputCity) {
        return cityList.stream().filter(city -> city.equals(inputCity)).findFirst().orElse(null);
    }

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(getCityFromBoardList(new City(ATLANTA.cityName, ATLANTA.virusType)));
    }

    @Test
    void isDoable_limaIsNotNearToAtlanta_thenFalse () {
        assertFalse(dispatcherAllPlayerMovementsService.isDoable(player, getCityFromBoardList(new City(LIMA.cityName, LIMA.virusType))));
    }

    @Test
    void isDoable_chicagoIsNearToAtlanta_thenTrue() {
        assertTrue(dispatcherAllPlayerMovementsService.isDoable(player, getCityFromBoardList(new City(CHICAGO.cityName, CHICAGO.virusType))));
    }

    @Test
    void doAction_limaIsNotNearToAtlanta_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> dispatcherAllPlayerMovementsService.doAction(player, new City(LIMA.cityName, LIMA.virusType)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DRIVEFERRY.label));
        assertTrue(actualMessage.contains(DRIVEFERRY_ERROR_NO_CONNECTION));
    }

    @Test
    void doAction_chicagoIsNearToAtlanta() throws ActionException  {
        dispatcherAllPlayerMovementsService.doAction(player, new City(CHICAGO.cityName, CHICAGO.virusType));
        assertEquals(player.getCity(), new City(CHICAGO.cityName, CHICAGO.virusType));
    }

    @Test
    void returnAvailableActions_3Players() throws ActionException  {
        MedicPlayer medic = new MedicPlayer();
        medic.setCity(getCityFromBoardList(new City(ATLANTA.cityName, ATLANTA.virusType)));
        DispatcherPlayer dispatcher = new DispatcherPlayer();
        dispatcher.setCity(getCityFromBoardList(new City(LIMA.cityName, LIMA.virusType)));
        QuarantinePlayer quarantine = new QuarantinePlayer();
        quarantine.setCity(getCityFromBoardList(new City(LAGOS.cityName, LAGOS.virusType)));

        List<Action> actionsToValidate = dispatcherAllPlayerMovementsService.returnAvailableActions(new ArrayList<>(Arrays.asList(medic, dispatcher, quarantine)));

        assertEquals(6, actionsToValidate.size());
        assertEquals(6, actionsToValidate.stream().filter(x -> DRIVEFERRYDISPATCHER.equals(x.getActionsType())).count());
        assertEquals(3, actionsToValidate.stream().filter(x -> MEDIC_NAME.equals(x.getPlayer().getName())).count());
        assertEquals(3, actionsToValidate.stream().filter(x -> QUARANTINE_NAME.equals(x.getPlayer().getName())).count());

        actionsToValidate.get(0).execute();
        assertEquals(CHICAGO.cityName, medic.getCity().getName());
    }

}
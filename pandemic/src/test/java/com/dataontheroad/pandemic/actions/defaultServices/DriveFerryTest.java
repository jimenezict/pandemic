package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.Player;
import com.dataontheroad.pandemic.board.model.enums.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;
import static org.junit.jupiter.api.Assertions.*;

class DriveFerryTest {

    List<City> emptyNodeCityConnection = new ArrayList<>();
    private City santiago = new City("Santiago de Chile", VirusType.YELLOW, emptyNodeCityConnection);
    private City mexico = new City("Ciudad de Mexico", VirusType.YELLOW, emptyNodeCityConnection);
    private City bogota = new City("Bogota", VirusType.YELLOW, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, new ArrayList<City>(Arrays.asList(santiago, mexico, bogota)));
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    Player player;

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(lima);
    }

    @Test
    public void isDoable_calcutaIsNotNearToLima_thenFalse() {
        assertFalse(DriveFerryDefaultService.isDoable(player, calculta));
    }

    @Test
    public void isDoable_bogotaIsNearToLima_thenFalse() {
        assertTrue(DriveFerryDefaultService.isDoable(player, bogota));
    }

    @Test
    public void doAction_calcutaIsNotNearToLima_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> DriveFerryDefaultService.doAction(player, calculta));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.DRIVEFERRY.label));
        assertTrue(actualMessage.contains(DRIVEFERRY_ERROR_NO_CONNECTION));
    }

    @Test
    public void doAction_playerHasMoveToBogota() throws ActionException  {
        DriveFerryDefaultService.doAction(player, bogota);
        assertEquals(player.getCity(), bogota);
    }
}
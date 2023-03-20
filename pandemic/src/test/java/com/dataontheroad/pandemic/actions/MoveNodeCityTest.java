package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class MoveNodeCityTest {

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
        assertFalse(MoveNodeCity.isDoable(player, calculta));
    }

    @Test
    public void isDoable_bogotaIsNearToLima_thenFalse() {
        assertTrue(MoveNodeCity.isDoable(player, bogota));
    }

    @Test
    public void doAction_calcutaIsNotNearToLima_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> MoveNodeCity.doAction(player, calculta));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.MOVENODECITY.label));
        assertTrue(actualMessage.contains("Move to near city when there is a direct connection"));
    }

    @Test
    public void doAction_playerHasMoveToBogota() throws ActionException  {
        MoveNodeCity.doAction(player, bogota);
        assertEquals(player.getCity(), bogota);
    }
}
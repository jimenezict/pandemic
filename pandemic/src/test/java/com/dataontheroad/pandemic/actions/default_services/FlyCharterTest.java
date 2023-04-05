package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.virus.VirusType;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.board.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYCHARTER_ERROR_NO_CARD;
import static org.junit.jupiter.api.Assertions.*;

class FlyCharterTest {

    List<City> emptyNodeCityConnection = new ArrayList<>();
    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private City atlanta = new City("Atlanta", VirusType.BLUE, emptyNodeCityConnection);
    Player player;

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(atlanta);

        List<CityCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        player.setListCard(cardList);
    }

    @Test
    void isDoable_atlantaIsNotOnPlayerHand_thenFalse() {
        assertFalse(FlyCharterDefaultService.isDoable(player));
    }

    @Test
    void isDoable_atlantaIsOnPlayerHand_thenTrue() {
        player.getListCard().add(createCityCard(atlanta));
        assertTrue(FlyCharterDefaultService.isDoable(player));
    }

    @Test
    void doAction_atlantaIsNotOnPlayerHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> FlyCharterDefaultService.doAction(player, tokio));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.FLYCHARTER.label));
        assertTrue(actualMessage.contains(FLYCHARTER_ERROR_NO_CARD));
    }

    @Test
    void doAction_playerHasMoveToTokioAndAtlantaCardIsNotOnHand() throws ActionException  {
        player.getListCard().add(createCityCard(atlanta));
        FlyCharterDefaultService.doAction(player, tokio);
        assertEquals(player.getCity(), tokio);
        assertFalse(player.getListCard().contains(createCityCard(atlanta)));
    }

}
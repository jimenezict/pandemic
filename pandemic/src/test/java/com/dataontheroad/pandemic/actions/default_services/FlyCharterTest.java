package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYCHARTER_ERROR_INCORRECT_DESTINATION;
import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYCHARTER_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
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

    FlyCharterDefaultService flyCharterDefaultService = new FlyCharterDefaultService();

    @BeforeEach
    public void setPlayer() {
        player = new Player();
        player.setCity(atlanta);

        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        player.setListCard(cardList);
    }

    @Test
    void isDoable_atlantaIsNotOnPlayerHand_thenFalse() {
        assertFalse(flyCharterDefaultService.isDoable(player));
    }

    @Test
    void isDoable_atlantaIsOnPlayerHand_thenTrue() {
        player.getListCard().add(createCityCard(atlanta));
        assertTrue(flyCharterDefaultService.isDoable(player));
    }

    @Test
    void doAction_atlantaIsNotOnPlayerHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> flyCharterDefaultService.doAction(player, tokio));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.FLYCHARTER.label));
        assertTrue(actualMessage.contains(FLYCHARTER_ERROR_NO_CARD));
    }

    @Test
    void doAction_playerInAtlantaFlyToAtlanta_throwException() {
        player.getListCard().add(createCityCard(atlanta));

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> flyCharterDefaultService.doAction(player, atlanta));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.FLYCHARTER.label));
        assertTrue(actualMessage.contains(FLYCHARTER_ERROR_INCORRECT_DESTINATION));
    }

    @Test
    void doAction_playerHasMoveToTokioAndAtlantaCardIsNotOnHand() throws ActionException  {
        player.getListCard().add(createCityCard(atlanta));
        flyCharterDefaultService.doAction(player, tokio);
        assertEquals(player.getCity(), tokio);
        assertFalse(player.getListCard().contains(createCityCard(atlanta)));
    }

}
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

import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYDIRECT_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class FlyDirectCityDefaultServiceTest {

    final List<City> emptyNodeCityConnection = new ArrayList<>();
    private final City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private final City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private final City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private final City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private final City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private final City atlanta = new City("Atlanta", VirusType.BLUE, emptyNodeCityConnection);
    Player player;

    final FlyDirectCityDefaultService flyDirectCityDefaultService = FlyDirectCityDefaultService.getInstance();

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
    void isDoable_tokioIsNotOnPlayerHand_thenFalse() {
        assertFalse(flyDirectCityDefaultService.isDoable(player, tokio));
    }

    @Test
    void isDoable_limaIsOnPlayerHand_thenTrue() {
        assertTrue(flyDirectCityDefaultService.isDoable(player, lima));
    }

    @Test
    void doAction_tokioIsNotOnPlayerHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> flyDirectCityDefaultService.doAction(player, tokio));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.FLYDIRECT.label));
        assertTrue(actualMessage.contains(FLYDIRECT_ERROR_NO_CARD));
    }

    @Test
    void doAction_playerHasMoveToLima() throws ActionException  {
        flyDirectCityDefaultService.doAction(player, lima);
        assertEquals(player.getCity(), lima);
        assertFalse(player.getListCard().contains(createCityCard(lima)));
    }

}
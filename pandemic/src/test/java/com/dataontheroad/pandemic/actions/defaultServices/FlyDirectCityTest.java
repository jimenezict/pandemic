package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.board.model.Card;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.Player;
import com.dataontheroad.pandemic.board.model.enums.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.Literals.FLYDIRECT_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.board.model.Card.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class FlyDirectCityTest {

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

        List<Card> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        player.setListCard(cardList);
    }

    @Test
    public void isDoable_tokioIsNotOnPlayerHand_thenFalse() {
        assertFalse(FlyDirectCity.isDoable(player, tokio));
    }

    @Test
    public void isDoable_limaIsOnPlayerHand_thenTrue() {
        assertTrue(FlyDirectCity.isDoable(player, lima));
    }

    @Test
    public void doAction_tokioIsNotOnPlayerHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> FlyDirectCity.doAction(player, tokio));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.FLYDIRECT.label));
        assertTrue(actualMessage.contains(FLYDIRECT_ERROR_NO_CARD));
    }

    @Test
    public void doAction_playerHasMoveToLima() throws ActionException  {
        FlyDirectCity.doAction(player, lima);
        assertEquals(player.getCity(), lima);
        assertFalse(player.getListCard().contains(createCityCard(lima)));
    }

}
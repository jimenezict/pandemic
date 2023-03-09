package com.dataontheroad.pandemic.helper;

import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.Virus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.helper.ActionsHelper.playerHasCardForHisPosition;
import static com.dataontheroad.pandemic.helper.ActionsHelper.playerRemoveCardFromDeck;
import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class ActionsHelperTest {

    Player player;

    private City newyork = new City("New York", Virus.BLUE);
    private City calculta = new City("Calcuta", Virus.BLACK);
    private City essen = new City("Essen", Virus.BLUE);
    private City lima = new City("Lima", Virus.YELLOW);
    private City tokio = new City("Tokio", Virus.RED);

    @BeforeEach
    public void setPlayer() {
        player = new Player();

        List<Card> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        player.setListCard(cardList);
    }

    @Test
    void playerRemoveCardFromDeck_whenCardExists(){
        playerRemoveCardFromDeck(player, createCityCard(newyork));
        assertEquals(3, player.getListCard().size());
    }

    @Test
    void playerRemoveCardFromDeck_whenCardDoNotExists(){
        playerRemoveCardFromDeck(player, createCityCard(tokio));
        assertEquals(4, player.getListCard().size());
    }

    @Test
    void playerRemoveCardFromDeck_whenCardDoNotExists_BecauseListIsEmpty(){
        player.setListCard(new ArrayList<>());
        playerRemoveCardFromDeck(player, createCityCard(newyork));
        assertEquals(0, player.getListCard().size());
    }

    @Test
    void playerHasCardForHisPosition_whenHasCardForLima_IsOnLima_ReturnsTrue() {
        assertTrue(playerHasCardForHisPosition(player, lima));
    }

    @Test
    void playerHasCardForHisPosition_whenHasNoCardForTokio_IsOnTokio_ReturnsFalse() {
        assertFalse(playerHasCardForHisPosition(player, tokio));
    }

}
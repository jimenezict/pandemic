package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.VirusType;
import com.dataontheroad.pandemic.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.actions.ActionsHelper.playerHasCardForHisPosition;
import static com.dataontheroad.pandemic.actions.ActionsHelper.playerRemoveCardFromDeck;
import static org.junit.jupiter.api.Assertions.*;

class ActionsHelperTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);

    @BeforeEach
    public void setPlayer() {
        player = new Player();

        List<BaseCard> cardList = new ArrayList<>();
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
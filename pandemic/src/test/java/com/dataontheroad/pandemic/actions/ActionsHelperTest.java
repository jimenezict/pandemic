package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.ScientistPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsHelper.*;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class ActionsHelperTest {

    Player player;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private City madrid = new City("Madrid", VirusType.BLUE, emptyNodeCityConnection);
    private City london = new City("London", VirusType.BLUE, emptyNodeCityConnection);
    private City washington = new City("Washington", VirusType.BLUE, emptyNodeCityConnection);

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
        playerRemoveCardFromHand(player, createCityCard(newyork));
        assertEquals(3, player.getListCard().size());
    }

    @Test
    void playerRemoveCardFromDeck_whenCardDoNotExists(){
        playerRemoveCardFromHand(player, createCityCard(tokio));
        assertEquals(4, player.getListCard().size());
    }

    @Test
    void playerRemoveCardFromDeck_whenCardDoNotExists_BecauseListIsEmpty(){
        player.setListCard(new ArrayList<>());
        playerRemoveCardFromHand(player, createCityCard(newyork));
        assertEquals(0, player.getListCard().size());
    }

    @Test
    void playerHasCardForHisPosition_whenHasCardForLima_IsOnLima_ReturnsTrue() {
        
        assertTrue(playerHasCardForHisLocation(player, lima));
    }

    @Test
    void playerHasCardForHisPosition_whenHasNoCardForTokio_IsOnTokio_ReturnsFalse() {
        assertFalse(playerHasCardForHisLocation(player, tokio));
    }

    @Test
    void playerHasEnoughCars_whenIsRegularPlayerAndHas4BlueCards_ReturnFalse() {
        List<BaseCard> cardList = player.getListCard();
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(london));
        assertFalse(playerHasEnoughCards(player, VirusType.BLUE));
    }

    @Test
    void playerHasEnoughCars_whenIsRegularPlayerAndHas5BlueCards_ReturnTrue() {
        List<BaseCard> cardList = player.getListCard();
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(london));
        cardList.add(createCityCard(washington));
        assertTrue(playerHasEnoughCards(player, VirusType.BLUE));

    }

    @Test
    void scientistHasEnoughCars_whenIsRegularPlayerAndHas3BlueCards_ReturnFalse() {
        Player scientist = new ScientistPlayer();
        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));
        cardList.add(createCityCard(madrid));

        scientist.setListCard(cardList);
        assertFalse(playerHasEnoughCards(scientist, VirusType.BLUE));
    }

    @Test
    void scientistHasEnoughCars_whenIsRegularPlayerAndHas4BlueCards_ReturnTrue() {
        Player scientist = new ScientistPlayer();
        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(london));

        scientist.setListCard(cardList);
        assertTrue(playerHasEnoughCards(scientist, VirusType.BLUE));
    }

}
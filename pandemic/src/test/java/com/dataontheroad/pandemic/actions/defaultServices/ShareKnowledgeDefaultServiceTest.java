package com.dataontheroad.pandemic.actions.defaultServices;

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
import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static org.junit.jupiter.api.Assertions.*;

class ShareKnowledgeDefaultServiceTest {
    Player sender, receiver;

    List<City> emptyNodeCityConnection = new ArrayList<>();

    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calculta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City lima = new City("Lima", VirusType.YELLOW, emptyNodeCityConnection);
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private City cairo =  new City("Cairo", VirusType.BLACK, emptyNodeCityConnection);
    private City argel =  new City("Argel", VirusType.BLACK, emptyNodeCityConnection);
    private City buenosaires = new City("Buenos Aires", VirusType.YELLOW, emptyNodeCityConnection);
    private City madrid = new City("Madrid", VirusType.BLUE, emptyNodeCityConnection);
    private City paris = new City("Paris", VirusType.BLUE, emptyNodeCityConnection);
    private City atlanta = new City("Atlanta", VirusType.BLUE, emptyNodeCityConnection);

    @BeforeEach
    public void setPlayer() {
        sender = new Player();
        sender.setCity(newyork);

        List<CityCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        sender.setListCard(cardList);

        receiver = new Player();
        receiver.setCity(newyork);

        cardList = new ArrayList<>();
        cardList.add(createCityCard(tokio));
        cardList.add(createCityCard(cairo));
        cardList.add(createCityCard(argel));
        cardList.add(createCityCard(buenosaires));

        receiver.setListCard(cardList);
    }

    @Test
    public void isDoable_sharingRightCardOnRightCity_thenTrue() {
        assertTrue(ShareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    public void isDoable_sharingCardWhichIsNotOnTheSendersHand_thenFalse() {
        assertFalse(ShareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(cairo)));
    }

    @Test
    public void isDoable_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_thenFalse() {
        assertFalse(ShareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(calculta)));
    }

    @Test
    public void isDoable_sharingRightCardOnRightCityButReceiverIsFull_thenFalse() {
        List<CityCard> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        assertFalse(ShareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    public void isDoable_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_thenFalse() {
        receiver.setCity(madrid);
        assertFalse(ShareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    public void doAction_sharingCardWhichIsNotOnTheSendersHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(cairo)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NO_CARD));
    }

    @Test
    public void doAction_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(calculta)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NOT_CARD_CITY));
    }

    @Test
    public void doAction_sharingRightCardOnRightCityButReceiverIsFull_throwException() {
        List<CityCard> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_OVERCAPACITY));
    }

    @Test
    public void doAction_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_throwException() {
        receiver.setCity(madrid);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NOT_SAME_CITY));
    }

    @Test
    public void doAction_transferCard() throws ActionException {
        CityCard newyorkCard = createCityCard(newyork);
        ShareKnowledgeDefaultService.doAction(sender, receiver, newyorkCard);
        assertFalse(sender.getListCard().contains(newyorkCard));
        assertTrue(receiver.getListCard().contains(newyorkCard));
    }
}
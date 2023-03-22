package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.actions.services.ShareKnowledge;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.Literals.*;
import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class ShareKnowledgeTest {
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

        List<Card> cardList = new ArrayList<>();
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
        assertTrue(ShareKnowledge.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    public void isDoable_sharingCardWhichIsNotOnTheSendersHand_thenFalse() {
        assertFalse(ShareKnowledge.isDoable(sender, receiver, createCityCard(cairo)));
    }

    @Test
    public void isDoable_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_thenFalse() {
        assertFalse(ShareKnowledge.isDoable(sender, receiver, createCityCard(calculta)));
    }

    @Test
    public void isDoable_sharingRightCardOnRightCityButReceiverIsFull_thenFalse() {
        List<Card> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        assertFalse(ShareKnowledge.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    public void isDoable_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_thenFalse() {
        receiver.setCity(madrid);
        assertFalse(ShareKnowledge.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    public void doAction_sharingCardWhichIsNotOnTheSendersHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledge.doAction(sender, receiver, createCityCard(cairo)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NO_CARD));
    }

    @Test
    public void doAction_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledge.doAction(sender, receiver, createCityCard(calculta)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NOT_CARD_CITY));
    }

    @Test
    public void doAction_sharingRightCardOnRightCityButReceiverIsFull_throwException() {
        List<Card> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledge.doAction(sender, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_OVERCAPACITY));
    }

    @Test
    public void doAction_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_throwException() {
        receiver.setCity(madrid);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledge.doAction(sender, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NOT_SAME_CITY));
    }

    @Test
    public void doAction_transferCard() throws ActionException {
        Card newyorkCard = createCityCard(newyork);
        ShareKnowledge.doAction(sender, receiver, newyorkCard);
        assertFalse(sender.getListCard().contains(newyorkCard));
        assertTrue(receiver.getListCard().contains(newyorkCard));
    }
}
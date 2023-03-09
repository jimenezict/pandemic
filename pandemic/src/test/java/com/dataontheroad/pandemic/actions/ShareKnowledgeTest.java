package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Card;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.Virus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.model.Card.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class ShareKnowledgeTest {
    Player sender, receiver;

    private City newyork = new City("New York", Virus.BLUE);
    private City calculta = new City("Calcuta", Virus.BLACK);
    private City essen = new City("Essen", Virus.BLUE);
    private City lima = new City("Lima", Virus.YELLOW);
    private City tokio = new City("Tokio", Virus.RED);
    private City cairo =  new City("Cairo", Virus.BLACK);
    private City argel =  new City("Argel", Virus.BLACK);
    private City buenosaires = new City("Buenos Aires", Virus.YELLOW);
    private City atlanta = new City("Atlanta", Virus.BLUE);
    private City madrid = new City("Madrid", Virus.BLUE);
    private City paris = new City("Paris", Virus.BLUE);

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
        assertTrue(actualMessage.contains("Sender do not have the Card"));
    }

    @Test
    public void doAction_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledge.doAction(sender, receiver, createCityCard(calculta)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains("Sender is not on the Card city"));
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
        assertTrue(actualMessage.contains("Receiver has overpass 7 cards hand capacity"));
    }

    @Test
    public void doAction_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_throwException() {
        receiver.setCity(madrid);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledge.doAction(sender, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains("Sender and receiver are not on same city"));
    }

    @Test
    public void doAction_transferCard() throws ActionException {
        Card newyorkCard = createCityCard(newyork);
        ShareKnowledge.doAction(sender, receiver, newyorkCard);
        assertFalse(sender.getListCard().contains(newyorkCard));
        assertTrue(receiver.getListCard().contains(newyorkCard));
    }
}
package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
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
    ShareKnowledgeDefaultService shareKnowledgeDefaultService= new ShareKnowledgeDefaultService();

    @BeforeEach
    public void setPlayer() {
        sender = new Player();
        sender.setCity(newyork);

        List<BaseCard> cardList = new ArrayList<>();
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
    void isDoable_sharingRightCardOnRightCity_thenTrue() {
        assertTrue(shareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    void isDoable_sharingCardWhichIsNotOnTheSendersHand_thenFalse() {
        assertFalse(shareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(cairo)));
    }

    @Test
    void isDoable_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_thenFalse() {
        assertFalse(shareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(calculta)));
    }

    @Test
    void isDoable_sharingRightCardOnRightCityButReceiverIsFull_thenFalse() {
        List<BaseCard> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        assertFalse(shareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    void isDoable_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_thenFalse() {
        receiver.setCity(madrid);
        assertFalse(shareKnowledgeDefaultService.isDoable(sender, receiver, createCityCard(newyork)));
    }

    @Test
    void doAction_sharingCardWhichIsNotOnTheSendersHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> shareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(cairo)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NO_CARD));
    }

    @Test
    void doAction_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> shareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(calculta)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NOT_CARD_CITY));
    }

    @Test
    void doAction_sharingRightCardOnRightCityButReceiverIsFull_throwException() {
        List<BaseCard> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> shareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_OVERCAPACITY));
    }

    @Test
    void doAction_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_throwException() {
        receiver.setCity(madrid);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> shareKnowledgeDefaultService.doAction(sender, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(ActionsType.SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NOT_SAME_CITY));
    }

    @Test
    void doAction_transferCard() throws ActionException {
        CityCard newyorkCard = createCityCard(newyork);
        shareKnowledgeDefaultService.doAction(sender, receiver, newyorkCard);
        assertFalse(sender.getListCard().contains(newyorkCard));
        assertTrue(receiver.getListCard().contains(newyorkCard));
    }
}
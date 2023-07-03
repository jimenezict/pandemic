package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.default_services.ShareKnowledgeDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.ResearchPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.SHAREKNOWLEDGE;
import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class ShareKnowledgeResearchServiceTest {
    ResearchPlayer researchPlayer;
    Player receiver;

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
        researchPlayer = new ResearchPlayer();
        researchPlayer.setCity(newyork);

        List<BaseCard> cardList = new ArrayList<>();
        cardList.add(createCityCard(newyork));
        cardList.add(createCityCard(calculta));
        cardList.add(createCityCard(essen));
        cardList.add(createCityCard(lima));

        researchPlayer.setListCard(cardList);

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
        assertTrue(ShareKnowledgeResearchService.isDoable(researchPlayer, receiver, createCityCard(newyork)));
    }

    @Test
    void isDoable_sharingCardWhichIsNotOnTheSendersHand_thenFalse() {
        assertFalse(ShareKnowledgeResearchService.isDoable(researchPlayer, receiver, createCityCard(cairo)));
    }

    @Test
    void isDoable_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity_thenFalse() {
        assertTrue(ShareKnowledgeResearchService.isDoable(researchPlayer, receiver, createCityCard(calculta)));
    }

    @Test
    void isDoable_sharingRightCardOnRightCityButReceiverIsFull_thenFalse() {
        List<BaseCard> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        assertFalse(ShareKnowledgeResearchService.isDoable(researchPlayer, receiver, createCityCard(newyork)));
    }

    @Test
    void isDoable_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_thenFalse() {
        receiver.setCity(madrid);
        assertFalse(ShareKnowledgeResearchService.isDoable(researchPlayer, receiver, createCityCard(newyork)));
    }

    @Test
    void doAction_sharingCardWhichIsNotOnTheSendersHand_throwException() {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledgeResearchService.doAction(researchPlayer, receiver, createCityCard(cairo)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NO_CARD));
    }


    @Test
    void doAction_sharingRightCardOnRightCityButReceiverIsFull_throwException() {
        List<BaseCard> cardList = receiver.getListCard();
        cardList.add(createCityCard(atlanta));
        cardList.add(createCityCard(madrid));
        cardList.add(createCityCard(paris));

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledgeResearchService.doAction(researchPlayer, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_OVERCAPACITY));
    }

    @Test
    void doAction_sharingRightCardOnRightCityButReceiverIsOnAnotherCity_throwException() {
        receiver.setCity(madrid);
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> ShareKnowledgeResearchService.doAction(researchPlayer, receiver, createCityCard(newyork)));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE.label));
        assertTrue(actualMessage.contains(SHAREKNOWLEDGE_ERROR_NOT_SAME_CITY));
    }

    @Test
    void doAction_transferCard() throws ActionException {
        CityCard newyorkCard = createCityCard(newyork);
        ShareKnowledgeDefaultService.doAction(researchPlayer, receiver, newyorkCard);
        assertFalse(researchPlayer.getListCard().contains(newyorkCard));
        assertTrue(receiver.getListCard().contains(newyorkCard));
    }

    @Test
    void doAction_sharingCardWhichIsOnTheSendersHandButIsNotOnCurrentCity() throws ActionException {
        int initialNumberResearcPlayer = researchPlayer.getListCard().size();
        int initialNumberReceiver = receiver.getListCard().size();
        ShareKnowledgeResearchService.doAction(researchPlayer, receiver, createCityCard(calculta));

        assertEquals(initialNumberResearcPlayer - 1, researchPlayer.getListCard().size());
        assertEquals(initialNumberReceiver + 1, receiver.getListCard().size());
    }

    @Test
    void returnAvailableActions() {
        //both in NewYork, with 4 cards each one
        List<Action> actionsAvailable = ShareKnowledgeResearchService.returnAvailableActions(researchPlayer, Arrays.asList(receiver));
        assertEquals(8, actionsAvailable.stream().filter(action -> SHAREKNOWLEDGE.equals(action.getActionsType())).count());
        assertEquals(4, actionsAvailable.stream().filter(action -> receiver.equals(action.getPlayer())).count());
        assertEquals(4, actionsAvailable.stream().filter(action -> researchPlayer.equals(action.getPlayer())).count());
    }

}
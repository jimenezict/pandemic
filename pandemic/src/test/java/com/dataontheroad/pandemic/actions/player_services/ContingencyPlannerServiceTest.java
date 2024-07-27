package com.dataontheroad.pandemic.actions.player_services;


import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.TakeDiscardEventCardAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.GovernmentGrantEventCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.CONTINGENCY_ERROR_HAS_EXTRA_CARD_ALREADY;
import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createPlayerQueue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.CollectionUtils.isEmpty;

class ContingencyPlannerServiceTest {

    List<BaseCard> discardedCards = new ArrayList<>();
    ContingencyPlannerService contingencyPlannerService;
    SpecialCard eventCard;
    ContingencyPlayer player;

    @BeforeEach
    public void setUp() {
        discardedCards = createPlayerQueue(2);
        contingencyPlannerService = ContingencyPlannerService.getInstance();
        player = new ContingencyPlayer();
        eventCard = new GovernmentGrantEventCard();
        Collections.shuffle(discardedCards);
    }

    @Test
    void isDoable_falseBecauseThereAreNoEventCards() {
        assertFalse(contingencyPlannerService.isDoable(player, discardedCards));
    }

    @Test
    void isDoable_trueBecauseThereAreEventCards() {
        discardedCards.add(eventCard);
        assertTrue(contingencyPlannerService.isDoable(player, discardedCards));
    }

    @Test
    void returnAvailableActions_returnsEmptyArray() {
        assertTrue(isEmpty(contingencyPlannerService.returnAvailableActions(player, discardedCards)));
    }

    @Test
    void returnAvailableActions_returnsArray() {
        discardedCards.add(eventCard);
        List<Action> actionList = contingencyPlannerService.returnAvailableActions(player, discardedCards);
        assertEquals(1, actionList.size());
        assertEquals(ActionsType.TAKEDISCARDEVENTCARD, actionList.get(0).getActionsType());
        assertEquals(eventCard, ((TakeDiscardEventCardAction) actionList.get(0)).getEventCard());
    }

    @Test
    void doAction_isCorrectWhenCardIsOnTheDiscardDeck() throws ActionException {
        discardedCards.add(eventCard);
        contingencyPlannerService.doAction(player, discardedCards, eventCard);
        assertEquals(eventCard, player.getExtraEventCard());
    }

    @Test
    void doAction_isEmptyWhenCardIsOnTheDiscardDeck() throws ActionException {
        contingencyPlannerService.doAction(player, discardedCards, eventCard);
        assertNull(player.getExtraEventCard());
    }

    @Test
    void doAction_throwExceptionWhenPlayerHasAlreadyCard() {
        player.setExtraEventCard(eventCard);

        ActionException exception =
                assertThrows(ActionException.class,
                        () -> contingencyPlannerService.doAction(player, discardedCards, eventCard));
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(CONTINGENCY_ERROR_HAS_EXTRA_CARD_ALREADY));
        assertTrue(actualMessage.contains(ActionsType.PLAYERACTION.label));
    }

}
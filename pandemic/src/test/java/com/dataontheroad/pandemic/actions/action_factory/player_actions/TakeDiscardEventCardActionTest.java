package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.default_services.BuildResearchCenterDefaultService;
import com.dataontheroad.pandemic.actions.player_services.ContingencyPlannerService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.GovernmentGrantEventCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.constants.LiteralsCard.SPECIAL_EVENT_GOVERNMENT_GRANT_NAME;
import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createCityDeck;
import static org.junit.jupiter.api.Assertions.*;

class TakeDiscardEventCardActionTest {

    ContingencyPlayer contingencyPlayer;
    SpecialCard eventCard;

    @BeforeEach
    public void setUp() {
        contingencyPlayer = new ContingencyPlayer();
        eventCard = new GovernmentGrantEventCard();
    }
    @Test
    void takeDiscardEventCardAction_whenPlayerIsContingence() throws ActionException {
        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard);

        assertEquals(SPECIAL_EVENT_GOVERNMENT_GRANT_NAME, takeDiscardEventCardAction.getEventCard().getEventName());
        assertEquals(ActionsType.TAKEDISCARDEVENTCARD, takeDiscardEventCardAction.getActionsType());
    }

    @Test
    void takeDiscardEventCardAction_actionPrompt() throws ActionException {
        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard);

        assertEquals(TAKEDISCARDEVENTCARD_ACTION + eventCard.getEventName(), takeDiscardEventCardAction.actionPrompt());
    }

    @Test
    void executeSuccessTest() throws ActionException {
        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard);

        List<BaseCard> discardedCards = createCityDeck(2);
        discardedCards.add(eventCard);
        Collections.shuffle(discardedCards);

        takeDiscardEventCardAction.setDiscardedCards(discardedCards);
        takeDiscardEventCardAction.execute();

    }

}
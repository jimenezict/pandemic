package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.default_services.BuildResearchCenterDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.special_card.GovernmentGrantEventCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import com.dataontheroad.pandemic.model.player.ScientistPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.constants.LiteralsCard.SPECIAL_EVENT_GOVERNMENT_GRANT_NAME;
import static org.junit.jupiter.api.Assertions.*;

class TakeDiscardEventCardActionTest {

    ContingencyPlayer contingencyPlayer;
    ScientistPlayer scientistPlayer;
    SpecialCard eventCard;

    @BeforeEach
    public void setUp() {
        contingencyPlayer = new ContingencyPlayer();
        scientistPlayer = new ScientistPlayer();
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
    void takeDiscardEventCardAction_whenPlayerIsNoContingence_throwException() throws ActionException {
        ActionException exception =
                assertThrows(ActionException.class,
                        () -> new TakeDiscardEventCardAction(scientistPlayer, eventCard));

        assertTrue(exception.getMessage().contains(CONTINGENCY_ERROR_NO_CONTINGENCY_PLAYER));
        assertTrue(exception.getMessage().contains(ActionsType.TAKEDISCARDEVENTCARD.label));
    }

    @Test
    void takeDiscardEventCardAction_actionPrompt() throws ActionException {
        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard);

        assertEquals(TAKEDISCARDEVENTCARD_ACTION + eventCard.getEventName(), takeDiscardEventCardAction.actionPrompt());
    }

}
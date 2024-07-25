package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.GovernmentGrantEventCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.TAKEDISCARDEVENTCARD_ACTION;
import static com.dataontheroad.pandemic.constants.LiteralsCard.SPECIAL_EVENT_GOVERNMENT_GRANT_NAME;
import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createPlayerQueue;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TakeDiscardEventCardActionTest {

    ContingencyPlayer contingencyPlayer;
    SpecialCard eventCard;
    List<BaseCard> discardedCards;
    private City newyork = new City("New York", VirusType.BLUE);
    private City calcuta = new City("Calcuta", VirusType.BLACK);

    @BeforeEach
    public void setUp() {
        contingencyPlayer = new ContingencyPlayer();
        eventCard = new GovernmentGrantEventCard();
        discardedCards = new ArrayList<>();
        discardedCards.add(createCityCard(newyork));
        discardedCards.add(createCityCard(calcuta));
        discardedCards.add(eventCard);
    }
    @Test
    void takeDiscardEventCardAction_whenPlayerIsContingence() throws ActionException {
        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard, discardedCards);

        assertEquals(SPECIAL_EVENT_GOVERNMENT_GRANT_NAME, takeDiscardEventCardAction.getEventCard().getEventName());
        assertEquals(ActionsType.TAKEDISCARDEVENTCARD, takeDiscardEventCardAction.getActionsType());
    }

    @Test
    void takeDiscardEventCardAction_actionPrompt() throws ActionException {
        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard, discardedCards);

        assertEquals(TAKEDISCARDEVENTCARD_ACTION + eventCard.getEventName(), takeDiscardEventCardAction.actionPrompt());
    }

    @Test
    void executeSuccessTest() throws ActionException {
        List<BaseCard> discardedCards = createPlayerQueue(2);

        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard, discardedCards);

        discardedCards.add(eventCard);
        Collections.shuffle(discardedCards);

        takeDiscardEventCardAction.execute();

        assertEquals(eventCard.getEventName(), contingencyPlayer.getExtraEventCard().getEventName());
        assertFalse(discardedCards.contains(contingencyPlayer.getExtraEventCard()));
        assertFalse(discardedCards.contains(eventCard));
    }

}
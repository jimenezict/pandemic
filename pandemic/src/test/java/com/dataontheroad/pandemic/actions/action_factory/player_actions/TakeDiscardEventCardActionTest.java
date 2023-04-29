package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.model.cards.model.special_card.GovernmentGrantEventCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import com.dataontheroad.pandemic.model.player.ScientistPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void takeDiscardEventCardAction_whenPlayerIsContingence() {
        TakeDiscardEventCardAction takeDiscardEventCardAction =
                new TakeDiscardEventCardAction(contingencyPlayer, eventCard);
    }

}
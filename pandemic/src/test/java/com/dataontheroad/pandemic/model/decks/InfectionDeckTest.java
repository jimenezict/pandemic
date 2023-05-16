package com.dataontheroad.pandemic.model.decks;

import com.dataontheroad.pandemic.model.cards.model.CityCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.List;

import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createInfectionDeck;
import static org.junit.jupiter.api.Assertions.*;

class InfectionDeckTest {

    private InfectionDeck infectionDeck;

    @BeforeEach
    public void setUp() {
        infectionDeck = createInfectionDeck();
    }

    @Test
    void validatesInitialization() {
        assertEquals(48 , infectionDeck.getInfectionDeck().size());
    }

    @Test
    void getInitialDrawInfectionDeck() {
        List<CityCard> cardsList = infectionDeck.getInitialDrawInfectionDeck();
        assertEquals(9 , cardsList.size());
        assertEquals(39 , infectionDeck.getInfectionDeck().size());
    }

    @Test
    void takeTopCardOfDeck() {
        CityCard cardsList = infectionDeck.takeTopCardOfDeck();
        assertNotNull(cardsList);
        assertEquals(47 , infectionDeck.getInfectionDeck().size());
    }

    @Test
    void takeBottomCardOfDeck() {
        CityCard cardsList = infectionDeck.takeBottomCardOfDeck();
        assertNotNull(cardsList);
        assertEquals(47 , infectionDeck.getInfectionDeck().size());
    }

}
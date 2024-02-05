package com.dataontheroad.pandemic.game.service.implementations;


import com.dataontheroad.pandemic.model.board.Board;
import com.dataontheroad.pandemic.model.city.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.model.board.BoardFactory.createBaseBoard;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeckManagementServiceImplTest {

    @InjectMocks
    DeckManagementServiceImpl deckManagementServiceImpl;

    Board board;

    @BeforeEach
    void setUp() {
        board = createBaseBoard();
    }

    @Test
    void getCardFromTopInfectionDesk() {
        int initialInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int initialInfectionDeck = board.getInfectionDeck().getDeck().size();

        City city = deckManagementServiceImpl.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        int finalInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int finalInfectionDeck = board.getInfectionDeck().getDeck().size();

        assertEquals(initialInfectionDiscardDeck + 1, finalInfectionDiscardDeck);
        assertEquals(initialInfectionDeck - 1, finalInfectionDeck);
        assertTrue(board.getInfectionDiscardDeck().contains(createCityCard(city)));
        assertFalse(board.getInfectionDeck().getDeck().contains(createCityCard(city)));
    }

    @Test
    void getCardFromBottomInfectionDesk() {
        int initialInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int initialInfectionDeck = board.getInfectionDeck().getDeck().size();

        City city = deckManagementServiceImpl.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        int finalInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int finalInfectionDeck = board.getInfectionDeck().getDeck().size();

        assertEquals(initialInfectionDiscardDeck + 1, finalInfectionDiscardDeck);
        assertEquals(initialInfectionDeck - 1, finalInfectionDeck);
        assertTrue(board.getInfectionDiscardDeck().contains(createCityCard(city)));
        assertFalse(board.getInfectionDeck().getDeck().contains(createCityCard(city)));
    }

    @Test
    void shuffleAndAtToTheTopOfDeck() {
        City city1 = deckManagementServiceImpl.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());
        City city2 = deckManagementServiceImpl.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());
        City city3 = deckManagementServiceImpl.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        City cityBottom = deckManagementServiceImpl.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        List<City> listOfCities = Arrays.asList(city1, city2, city3, cityBottom);

        deckManagementServiceImpl.shuffleAndAtToTheTopOfDeck(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        assertTrue(board.getInfectionDiscardDeck().isEmpty());

        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));

        assertFalse(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
    }

}
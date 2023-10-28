package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.model.board.Board;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dataontheroad.pandemic.model.board.BoardFactory.createBaseBoard;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class InfectionServiceImplTest {

    @InjectMocks
    InfectionServiceImpl infectionService;

    Board board;

    @BeforeEach
    void setUp() {
        board = createBaseBoard();
    }

    @Test
    void getCardFromTopInfectionDesk() {
        int initialInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int initialInfectionDeck = board.getInfectionDeck().getDeck().size();

        City city = infectionService.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

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

        City city = infectionService.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        int finalInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int finalInfectionDeck = board.getInfectionDeck().getDeck().size();

        assertEquals(initialInfectionDiscardDeck + 1, finalInfectionDiscardDeck);
        assertEquals(initialInfectionDeck - 1, finalInfectionDeck);
        assertTrue(board.getInfectionDiscardDeck().contains(createCityCard(city)));
        assertFalse(board.getInfectionDeck().getDeck().contains(createCityCard(city)));
    }

    @Test
    void shuffleAndAtToTheTopOfDeck() {
        City city1 = infectionService.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());
        City city2 = infectionService.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());
        City city3 = infectionService.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

    }

}
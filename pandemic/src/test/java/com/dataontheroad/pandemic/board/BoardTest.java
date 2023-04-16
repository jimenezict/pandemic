package com.dataontheroad.pandemic.board;

import com.dataontheroad.pandemic.board.cards.model.BaseCard;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dataontheroad.pandemic.board.cards.CardTypeEnum.EPIDEMIC;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void createBaseBoard() {
        Board board = new Board();
        board.getBoardCities().get(0);
        assertEquals(2, board.getNumberInfectionCard());
        assertEquals(48, board.getInfectionDeck().size());
        assertEquals(0, board.getOutBreaks());
        assertEquals(0, board.getPlayerDeck().stream().filter(x -> EPIDEMIC.equals(x.getCardType())).count());
        assertEquals(4, board.getVirusList().size());
    }

    @Test
    void valuesForTheNumberInfection() throws EndOfGameException {
        Board board = new Board();
        assertEquals(2, board.getNumberInfectionCard());
        board.increaseInfectionRate();
        assertEquals(2, board.getNumberInfectionCard());
        board.increaseInfectionRate();
        assertEquals(2, board.getNumberInfectionCard());
        board.increaseInfectionRate();
        assertEquals(3, board.getNumberInfectionCard());
        board.increaseInfectionRate();
        assertEquals(3, board.getNumberInfectionCard());
        board.increaseInfectionRate();
        assertEquals(4, board.getNumberInfectionCard());
        board.increaseInfectionRate();
        assertEquals(4, board.getNumberInfectionCard());

        EndOfGameException exception =
                assertThrows(EndOfGameException.class,
                        () -> board.increaseInfectionRate());

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("You had reach the maximal infection rate"));
    }

    @Test
    void valuesForTheOutbreak() throws EndOfGameException {
        Board board = new Board();
        for(int i=0; i<8; i++) {
            assertEquals(i, board.getOutBreaks());
            board.increaseOutBreaks();
        }
        assertEquals(8, board.getOutBreaks());

        EndOfGameException exception =
                assertThrows(EndOfGameException.class,
                        () -> board.increaseOutBreaks());

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains("You had reach the maximal number of outbreaks"));
    }

    @Test
    void getInitialDrawCardsWhenNumberOfPlayersIs2() {
        Board board = new Board();
        int initialSize = board.getPlayerDeck().size();
        List<BaseCard> initialDrawCards = board.getInitialDrawCards(2);
        assertEquals(4, initialDrawCards.size());
        assertEquals(initialSize - 4, board.getPlayerDeck().size());
    }

    @Test
    void getInitialDrawCardsWhenNumberOfPlayersIs3() {
        Board board = new Board();
        int initialSize = board.getPlayerDeck().size();
        List<BaseCard> initialDrawCards = board.getInitialDrawCards(3);
        assertEquals(3, initialDrawCards.size());
        assertEquals(initialSize - 3, board.getPlayerDeck().size());
    }

    @Test
    void getInitialDrawCardsWhenNumberOfPlayersIs4() {
        Board board = new Board();
        int initialSize = board.getPlayerDeck().size();
        List<BaseCard> initialDrawCards = board.getInitialDrawCards(4);
        assertEquals(2, initialDrawCards.size());
        assertEquals(initialSize - 2, board.getPlayerDeck().size());
    }

    @Test
    void getInitialDrawInfectionDeck() {
        Board board = new Board();
        List<CityCard> initialInfectionDraw = board.getInitialDrawInfectionDeck();
        assertEquals(9, initialInfectionDraw.size());
        assertEquals(39, board.getInfectionDeck().size());
        assertEquals(9, board.getInfectionDiscardDeck().size());
    }

}
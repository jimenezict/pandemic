package com.dataontheroad.pandemic.board;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.board.cards.CardTypeEnum.EPIDEMIC;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void createBoardWithTwoInfectionCard() {
        Board board = new Board(2);
        board.getBoardCities().get(0);
        assertEquals(2, board.getNumberInfectionCard());
        assertEquals(48, board.getInfectionDeck().size());
        assertEquals(0, board.getOutBreaks());
        assertEquals(2, board.getPlayerDeck().stream().filter(x -> EPIDEMIC.equals(x.getCardType())).count());
        assertEquals(4, board.getVirusList().size());
    }

    @Test
    void valuesForTheNumberInfection() throws EndOfGameException {
        Board board = new Board(2);
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
        Board board = new Board(2);
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

}
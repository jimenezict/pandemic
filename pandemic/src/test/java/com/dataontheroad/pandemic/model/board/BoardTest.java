package com.dataontheroad.pandemic.model.board;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EPIDEMIC;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void createBaseBoard() {
        Board board = new Board();
        board.getBoardCities().get(0);
        assertEquals(2, board.getNumberInfectionCard());
        assertEquals(48, board.getInfectionDeck().getInfectionDeck().size());
        assertEquals(0, board.getOutBreaks());
        assertEquals(0, board.getPlayerQueue().getPlayerQueue().stream().filter(x -> EPIDEMIC.equals(x.getCardType())).count());
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

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void getInitialDrawCardsWhenNumberOfPlayersIsN() {
        Board board = new Board();
        int initialSize = board.getPlayerQueue().getPlayerQueue().size();
        List<BaseCard> initialDrawCards = board.getPlayerQueue().getInitialDrawCards(2);
        assertEquals(4, initialDrawCards.size());
        assertEquals(initialSize - 4, board.getPlayerQueue().getPlayerQueue().size());
    }

}
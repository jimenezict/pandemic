package com.dataontheroad.pandemic.model.board;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.EpidemicCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.GovernmentGrantEventCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_MAX_INFECTION;
import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_MAX_OUTBREAK;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EPIDEMIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    @Test
    void createBaseBoard() {
        Board board = new Board();
        board.getBoardCities().get(0);
        assertEquals(2, board.getNumberInfectionCard());
        assertEquals(48, board.getInfectionDeck().getDeck().size());
        assertEquals(0, board.getOutBreaks());
        assertEquals(0, board.getPlayerQueue().getQueue().stream().filter(x -> EPIDEMIC.equals(x.getCardType())).count());
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

        assertEquals(END_OF_GAME_MAX_INFECTION, exception.getReasonOfEndGame());
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

        assertEquals(END_OF_GAME_MAX_OUTBREAK, exception.getReasonOfEndGame());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void getInitialDrawCardsWhenNumberOfPlayersIsN() {
        Board board = new Board();
        int initialSize = board.getPlayerQueue().getQueue().size();
        List<BaseCard> initialDrawCards = board.getPlayerQueue().getInitialDrawCards(2);
        assertEquals(4, initialDrawCards.size());
        assertEquals(initialSize - 4, board.getPlayerQueue().getQueue().size());
    }

    @Test
    void addCardOnPlayerDiscardDeck_doNotAddEpidemicCard_addGovernmentGrantSpecialAction() {
        Board board = new Board();
        board.addCardOnPlayerDiscardDeck(new EpidemicCard());
        assertEquals(0,board.getPlayerDiscardDeck().size());
        board.addCardOnPlayerDiscardDeck(new GovernmentGrantEventCard());
        assertEquals(1,board.getPlayerDiscardDeck().size());
    }
}
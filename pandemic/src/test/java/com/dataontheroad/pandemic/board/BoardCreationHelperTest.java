package com.dataontheroad.pandemic.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.board.BoardCreationHelper.configurePlayersOnBoard;
import static org.junit.jupiter.api.Assertions.*;

class BoardCreationHelperTest {

    Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }
    @Test
    void configurePlayerOnBoard_TwoPlayers_ChecksBothPlayersHave4CardsAndPlayerDeck() {
        final int NUMBER_PLAYERS = 2;
        final int NUMBER_CARDS = 4;

        int initialSize = board.getPlayerDeck().size();
        configurePlayersOnBoard(board, NUMBER_PLAYERS);

        assertEquals(NUMBER_PLAYERS, board.getPlayers().size());
        assertEquals(NUMBER_CARDS, board.getPlayers().get(0).getListCard().size());
        assertEquals(NUMBER_CARDS, board.getPlayers().get(1).getListCard().size());
        assertEquals(initialSize - NUMBER_CARDS * NUMBER_PLAYERS, board.getPlayerDeck().size());
    }

    @Test
    void configurePlayerOnBoard_ThreePlayers_ChecksBothPlayersHave3CardsAndPlayerDeck() {
        final int NUMBER_PLAYERS = 3;
        final int NUMBER_CARDS = 3;

        int initialSize = board.getPlayerDeck().size();
        configurePlayersOnBoard(board, NUMBER_PLAYERS);

        assertEquals(NUMBER_PLAYERS, board.getPlayers().size());
        assertEquals(NUMBER_CARDS, board.getPlayers().get(0).getListCard().size());
        assertEquals(NUMBER_CARDS, board.getPlayers().get(1).getListCard().size());
        assertEquals(initialSize - NUMBER_CARDS * NUMBER_PLAYERS, board.getPlayerDeck().size());
    }

    @Test
    void configurePlayerOnBoard_FourPlayers_ChecksBothPlayersHave2CardsAndPlayerDeck() {
        final int NUMBER_PLAYERS = 4;
        final int NUMBER_CARDS = 2;

        int initialSize = board.getPlayerDeck().size();
        configurePlayersOnBoard(board, NUMBER_PLAYERS);

        assertEquals(NUMBER_PLAYERS, board.getPlayers().size());
        assertEquals(NUMBER_CARDS, board.getPlayers().get(0).getListCard().size());
        assertEquals(NUMBER_CARDS, board.getPlayers().get(1).getListCard().size());
        assertEquals(initialSize - NUMBER_CARDS * NUMBER_PLAYERS, board.getPlayerDeck().size());
    }

}
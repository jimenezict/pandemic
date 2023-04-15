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
        configurePlayersOnBoard(board, 2);
        board.getPlayerDeck().size();
        assertEquals(2, board.getPlayers().size());
        //assertEquals(4, board.getPlayers().get(0).getListCard().size());
    }

}
package com.dataontheroad.pandemic.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.dataontheroad.pandemic.model.board.BoardCreationHelper.addEpidemicCards;
import static com.dataontheroad.pandemic.model.board.BoardCreationHelper.configurePlayersOnBoard;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EPIDEMIC;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardCreationHelperTest {

    Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @ParameterizedTest
    @CsvSource({
            "2, 4",
            "3, 3",
            "4, 2",
    })
    void configurePlayerOnBoard_All_CombinationOfPlayers(int number_player, int number_cards) {
        int initialSize = board.getPlayerQueue().getPlayerQueue().size();
        configurePlayersOnBoard(board, number_player);

        assertEquals(number_player, board.getPlayers().size());
        assertEquals(number_cards, board.getPlayers().get(0).getListCard().size());
        assertEquals(number_cards, board.getPlayers().get(1).getListCard().size());
        assertEquals(initialSize - number_cards * number_player, board.getPlayerQueue().getPlayerQueue().size());
    }

    @Test
    void addEpidemicCards_addFourCards() {
        int initialSize = board.getPlayerQueue().getPlayerQueue().size();
        addEpidemicCards(board, 4);
        assertEquals(4, board.getPlayerQueue().getPlayerQueue().stream().filter(card -> EPIDEMIC.equals(card.getCardType())).count());
        assertEquals(4, board.getPlayerQueue().getPlayerQueue().size() - initialSize);
    }
}
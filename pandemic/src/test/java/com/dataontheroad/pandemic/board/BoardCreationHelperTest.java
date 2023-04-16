package com.dataontheroad.pandemic.board;

import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dataontheroad.pandemic.board.BoardCreationHelper.configurePlayersOnBoard;
import static com.dataontheroad.pandemic.board.BoardCreationHelper.configureVirusOnBoard;
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

    @Test
    void configureVirusOnBoardTest() {
        List<CityCard> initialDrawInfection = configureVirusOnBoard(board);

        assertEquals(39, board.getInfectionDeck().size());
        assertEquals(9, board.getInfectionDiscardDeck().size());

        List<VirusType> cityVirusBoxes = board.getCityFromBoardList(initialDrawInfection.get(0).getCity()).getVirusBoxes();
        assertEquals(3, cityVirusBoxes.size());
        assertEquals(cityVirusBoxes.get(0), initialDrawInfection.get(0).getVirus());

        cityVirusBoxes = board.getCityFromBoardList(initialDrawInfection.get(3).getCity()).getVirusBoxes();
        assertEquals(2, cityVirusBoxes.size());
        assertEquals(cityVirusBoxes.get(0), initialDrawInfection.get(3).getVirus());

        cityVirusBoxes = board.getCityFromBoardList(initialDrawInfection.get(6).getCity()).getVirusBoxes();
        assertEquals(1, cityVirusBoxes.size());
        assertEquals(cityVirusBoxes.get(0), initialDrawInfection.get(6).getVirus());
    }
}
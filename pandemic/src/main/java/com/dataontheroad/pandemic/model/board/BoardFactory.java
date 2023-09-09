package com.dataontheroad.pandemic.model.board;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;

import static com.dataontheroad.pandemic.model.board.BoardCreationHelper.configurePlayersOnBoard;
import static com.dataontheroad.pandemic.model.board.BoardCreationHelper.configureVirusOnBoard;

public class BoardFactory {

    private BoardFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Board createBaseBoard() {
        return new Board();
    }

    public static Board createBoardWithNumberOfPlayers(int numOfPlayers) throws GameExecutionException {
        if(numOfPlayers < 2 || numOfPlayers > 4 )
            throw new GameExecutionException("Not valid argument for numOfPlayers:" + numOfPlayers);

        Board board = createBaseBoard();
        configurePlayersOnBoard(board, numOfPlayers);
        configureVirusOnBoard(board);
        return board;
    }
}

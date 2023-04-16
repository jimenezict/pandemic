package com.dataontheroad.pandemic.model.board;

import static com.dataontheroad.pandemic.model.board.BoardCreationHelper.configurePlayersOnBoard;
import static com.dataontheroad.pandemic.model.board.BoardCreationHelper.configureVirusOnBoard;

public class BoardFactory {

    public static Board createBaseBoard() {
        return new Board();
    }

    public static Board createBoardWithNumberOfPlayers(int numOfPlayers) throws Exception {
        if(numOfPlayers < 2 || numOfPlayers > 4 )
            throw new Exception("Not valid argument for numOfPlayers:" + numOfPlayers);

        Board board = createBaseBoard();
        configurePlayersOnBoard(board, numOfPlayers);
        configureVirusOnBoard(board);
        return board;
    }
}

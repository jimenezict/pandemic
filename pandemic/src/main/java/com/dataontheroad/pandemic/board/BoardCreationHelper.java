package com.dataontheroad.pandemic.board;

import com.dataontheroad.pandemic.board.player.Player;

import java.util.stream.IntStream;

public class BoardCreationHelper {

    public static void configurePlayersOnBoard(Board board, int numberOfPlayers) {
        IntStream.range(0, numberOfPlayers).forEach($ -> {
            Player player = new Player();
            player.setListCard(board.getInitialDrawCards(numberOfPlayers));
            board.addPlayer(player);
        });
    }

}

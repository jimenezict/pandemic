package com.dataontheroad.pandemic.board;

import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;

import java.util.List;
import java.util.stream.IntStream;

public class BoardCreationHelper {

    public static void configurePlayersOnBoard(Board board, int numberOfPlayers) {
        IntStream.range(0, numberOfPlayers).forEach($ -> {
            Player player = new Player();
            player.setListCard(board.getInitialDrawCards(numberOfPlayers));
            board.addPlayer(player);
        });
    }

    public static void configureVirusOnBoard(Board board) {
        List<CityCard> initialDrawInfection = board.getInitialDrawInfectionDeck();
        initialDrawInfection.forEach( city -> {
            City cityOnBoard = board.getBoardCities().get(board.getBoardCities().indexOf(city));
            cityOnBoard.getVirusBoxes().add(city.getVirus());
        });
        initialDrawInfection.subList(0,6).forEach( city -> {
            City cityOnBoard = board.getBoardCities().get(board.getBoardCities().indexOf(city));
            cityOnBoard.getVirusBoxes().add(city.getVirus());
        });
        initialDrawInfection.subList(0,3).forEach( city -> {
            City cityOnBoard = board.getBoardCities().get(board.getBoardCities().indexOf(city));
            cityOnBoard.getVirusBoxes().add(city.getVirus());
        });

    }

}

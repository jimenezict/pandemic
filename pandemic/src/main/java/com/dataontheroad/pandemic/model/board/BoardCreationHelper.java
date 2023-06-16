package com.dataontheroad.pandemic.model.board;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.cards.model.EpidemicCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class BoardCreationHelper {

    public static void configurePlayersOnBoard(Board board, int numberOfPlayers) {
        IntStream.range(0, numberOfPlayers).forEach($ -> {
            Player player = new Player();
            player.setListCard(board.getPlayerQueue().getInitialDrawCards(numberOfPlayers));
            player.setCity(board.getBoardCities().get(1));
            board.addPlayer(player);
        });
    }

    public static void addEpidemicCards(Board board, int numberOfEpidemicCards) {
        IntStream.range(0, numberOfEpidemicCards).forEach($ -> {
                    board.getPlayerQueue().getPlayerQueue().add(new EpidemicCard());
                });
        board.getPlayerQueue().shuffleQueue();
    }

    public static List<CityCard> configureVirusOnBoard(Board board) {
        List<CityCard> initialDrawInfection = board.getInfectionDeck().getInitialDrawInfectionDeck();
        board.setInfectionDiscardDeck(initialDrawInfection);
        initialDrawInfection.forEach( cityOnCard -> {
            City cityOnBoard = board.getCityFromBoardList(cityOnCard.getCity());
            cityOnBoard.getVirusBoxes().add(cityOnCard.getVirus());
        });
        initialDrawInfection.subList(0,6).forEach( cityOnCard -> {
            City cityOnBoard = board.getCityFromBoardList(cityOnCard.getCity());
            cityOnBoard.getVirusBoxes().add(cityOnCard.getVirus());
        });
        initialDrawInfection.subList(0,3).forEach( cityOnCard -> {
            City cityOnBoard = board.getCityFromBoardList(cityOnCard.getCity());
            cityOnBoard.getVirusBoxes().add(cityOnCard.getVirus());
        });
        return initialDrawInfection;
    }

}

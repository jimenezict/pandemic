package com.dataontheroad.pandemic.board.model;

import com.dataontheroad.pandemic.board.city.City;

import java.util.List;

public class Board {

    private List<Card> infectionDeck, infectionDiscardDeck;
    private List<Card> playerDeck, playerDiscardDeck;
    private List<City> boardCities;
    private List<Player> players;
    private List<Virus> virusList;
    int infectionRate, outbreaks;

    public Board(int numPlayers) {
        //the board will be initialized with reversing parameters, so will be provided from outside instead
        //of being generated on this object.
        /*
        infectionDiscardDeck = new ArrayList<>();
        playerDiscardDeck = new ArrayList<>();

        boardCities = initializedCities();
        infectionDeck = initializeInfectionDeck(boardCities);
        playerDeck = initializePlayerDeck(boardCities);
        virusList = initializeVirus();

        infectionRate = 0;
        outbreaks = 0;

        Collections.shuffle(infectionDeck);
        Collections.shuffle(playerDeck);

        initialCitiesInfection(boardCities);
        initialPlayersDraw(players, playerDeck);*/
    }

}

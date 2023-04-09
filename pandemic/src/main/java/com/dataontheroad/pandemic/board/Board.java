package com.dataontheroad.pandemic.board;

import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.board.virus.Virus;

import java.util.List;

public class Board {

    private List<CityCard> infectionDeck;
    private List<CityCard> infectionDiscardDeck;
    private List<CityCard> playerDeck;
    private List<CityCard> playerDiscardDeck;
    private List<City> boardCities;
    private List<Player> players;
    private List<Virus> virusList;
    private Integer infectionRate;
    private Integer outbreaks;

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

package com.dataontheroad.pandemic.board;

import com.dataontheroad.pandemic.board.cards.model.BaseCard;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.board.virus.Virus;
import com.dataontheroad.pandemic.board.virus.VirusType;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.dataontheroad.pandemic.board.cards.DeckCardFactory.createCityDeck;
import static com.dataontheroad.pandemic.board.cards.DeckCardFactory.createInfectionDeck;
import static com.dataontheroad.pandemic.board.city.CityFactory.createCityList;

public class Board {

    private static final int MAX_INFECTIONS = 6;
    private static final int MAX_OUTBREAKS = 8;
    private List<CityCard> infectionDeck;
    private List<CityCard> infectionDiscardDeck;
    private List<BaseCard> playerDeck;
    private List<BaseCard> playerDiscardDeck;
    private final List<City> boardCities;
    private List<Player> players;
    private final List<Virus> virusList;
    private Integer infectionRate;
    private Integer outbreaks;

    public Board() {
        infectionDiscardDeck = new ArrayList<>();
        playerDiscardDeck = new ArrayList<>();
        players = new ArrayList<>();

        boardCities = createCityList();
        infectionDeck = createInfectionDeck();
        playerDeck = createCityDeck();
        virusList = initializeVirus();

        infectionRate = 0;
        outbreaks = 0;
    }

    public List<CityCard> getInfectionDeck() {
        return infectionDeck;
    }

    public List<BaseCard> getPlayerDeck() {
        return playerDeck;
    }

    public List<City> getBoardCities() {
        return boardCities;
    }

    public List<Virus> getVirusList() {
        return virusList;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getOutBreaks() {
        return outbreaks;
    }

    public List<CityCard> getInfectionDiscardDeck() {
        return infectionDiscardDeck;
    }

    public int getNumberInfectionCard() {
        switch(infectionRate) {
            case 0:
            case 1:
            case 2:
                return 2;
            case 3:
            case 4:
                return 3;
            default:
                return 4;
        }
    }

    public void increaseInfectionRate() throws EndOfGameException {
        if(infectionRate >= MAX_INFECTIONS) {
            throw new EndOfGameException("You had reach the maximal infection rate");
        }
        infectionRate++;
    }

    public void increaseOutBreaks() throws EndOfGameException {
        if(outbreaks >= MAX_OUTBREAKS) {
            throw new EndOfGameException("You had reach the maximal number of outbreaks");
        }
        outbreaks++;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public City getCityFromBoardList(City inputCity) {
        return boardCities.stream().filter(city -> city.equals(inputCity)).findFirst().orElse(null);
    }

    protected List<BaseCard> getInitialDrawCards(int numberOfPlayers) {
        Collections.shuffle(playerDeck);
        List<BaseCard> playerCards = new ArrayList<> (playerDeck.subList(0, numberOfCardsToDraw(numberOfPlayers)));
        playerDeck.subList(0, numberOfCardsToDraw(numberOfPlayers)).clear();
        return playerCards;
    }

    protected List<CityCard> getInitialDrawInfectionDeck() {
        Collections.shuffle(infectionDeck);
        List<CityCard> initialInfectionDraw = new ArrayList<> (infectionDeck.subList(0, 9));
        infectionDiscardDeck.addAll(initialInfectionDraw);
        infectionDeck.subList(0, 9).clear();
        return initialInfectionDraw;
    }

    private static List<Virus> initializeVirus() {
        Virus blueVirus = new Virus(VirusType.BLUE);
        Virus blackVirus = new Virus(VirusType.BLACK);
        Virus redVirus = new Virus(VirusType.RED);
        Virus yellowVirus = new Virus(VirusType.YELLOW);

        return Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
    }

    private int numberOfCardsToDraw(int numberOfPlayers) {
        switch(numberOfPlayers) {
            case 2:
                return 4;
            case 3:
                return 3;
            case 4:
                return 2;
            default:
                return -1;
        }
    }
}

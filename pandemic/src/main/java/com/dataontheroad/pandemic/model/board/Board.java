package com.dataontheroad.pandemic.model.board;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createPlayerQueue;
import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createInfectionDeck;
import static com.dataontheroad.pandemic.model.city.CityFactory.createCityList;

public class Board {

    private static final int MAX_INFECTIONS = 6;
    private static final int MAX_OUTBREAKS = 8;
    private InfectionDeck infectionDeck;
    private List<CityCard> infectionDiscardDeck;
    private PlayerQueue playerQueue;
    private List<BaseCard> playerDiscardDeck;
    private final List<City> boardCities;
    private List<Player> players;
    private final List<Virus> virusList;
    private Integer infectionRate;
    private Integer outbreaks;

    protected Board() {
        infectionDiscardDeck = new ArrayList<>();
        playerDiscardDeck = new ArrayList<>();
        players = new ArrayList<>();

        boardCities = createCityList();
        infectionDeck = createInfectionDeck();
        playerQueue = createPlayerQueue();
        virusList = initializeVirus();

        infectionRate = 0;
        outbreaks = 0;
    }

    public InfectionDeck getInfectionDeck() {
        return infectionDeck;
    }

    public PlayerQueue getPlayerQueue() {
        return playerQueue;
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

    protected void addPlayer(Player player) {
        players.add(player);
    }

    public City getCityFromBoardList(City inputCity) {
        return boardCities.stream().filter(city -> city.equals(inputCity)).findFirst().orElse(null);
    }

    private static List<Virus> initializeVirus() {
        Virus blueVirus = new Virus(VirusType.BLUE);
        Virus blackVirus = new Virus(VirusType.BLACK);
        Virus redVirus = new Virus(VirusType.RED);
        Virus yellowVirus = new Virus(VirusType.YELLOW);

        return Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
    }

    public void setInfectionDiscardDeck(List<CityCard> infectionDiscardDeck) {
        this.infectionDiscardDeck = infectionDiscardDeck;
    }
}

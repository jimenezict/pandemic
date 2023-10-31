package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.model.board.Board;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.model.board.BoardFactory.createBaseBoard;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.model.city.CityEnum.ATLANTA;
import static com.dataontheroad.pandemic.model.city.CityEnum.PARIS;
import static com.dataontheroad.pandemic.model.virus.VirusType.BLUE;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class InfectionServiceImplTest {

    @InjectMocks
    InfectionServiceImpl infectionService;

    Board board;

    @BeforeEach
    void setUp() {
        board = createBaseBoard();
    }

    @Test
    void getCardFromTopInfectionDesk() {
        int initialInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int initialInfectionDeck = board.getInfectionDeck().getDeck().size();

        City city = infectionService.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        int finalInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int finalInfectionDeck = board.getInfectionDeck().getDeck().size();

        assertEquals(initialInfectionDiscardDeck + 1, finalInfectionDiscardDeck);
        assertEquals(initialInfectionDeck - 1, finalInfectionDeck);
        assertTrue(board.getInfectionDiscardDeck().contains(createCityCard(city)));
        assertFalse(board.getInfectionDeck().getDeck().contains(createCityCard(city)));
    }

    @Test
    void getCardFromBottomInfectionDesk() {
        int initialInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int initialInfectionDeck = board.getInfectionDeck().getDeck().size();

        City city = infectionService.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        int finalInfectionDiscardDeck = board.getInfectionDiscardDeck().size();
        int finalInfectionDeck = board.getInfectionDeck().getDeck().size();

        assertEquals(initialInfectionDiscardDeck + 1, finalInfectionDiscardDeck);
        assertEquals(initialInfectionDeck - 1, finalInfectionDeck);
        assertTrue(board.getInfectionDiscardDeck().contains(createCityCard(city)));
        assertFalse(board.getInfectionDeck().getDeck().contains(createCityCard(city)));
    }

    @Test
    void shuffleAndAtToTheTopOfDeck() {
        City city1 = infectionService.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());
        City city2 = infectionService.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());
        City city3 = infectionService.getCardFromTopInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        City cityBottom = infectionService.getCardFromBottomInfectionDesk(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        List<City> listOfCities = Arrays.asList(city1, city2, city3, cityBottom);

        infectionService.shuffleAndAtToTheTopOfDeck(board.getInfectionDeck(), board.getInfectionDiscardDeck());

        assertTrue(board.getInfectionDiscardDeck().isEmpty());

        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
        assertTrue(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));

        assertFalse(listOfCities.contains(board.getInfectionDeck().takeTopCardOfDeck().getCity()));
    }

    @Test
    void infectCity() {
        City atlanta = board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE));
        atlanta.setVirusBoxes(new ArrayList<>());
        infectionService.infectCity(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE)));
        assertEquals(1, atlanta.getVirusBoxes().size());

        assertNull(infectionService.infectCity(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE))));
        assertEquals(2, atlanta.getVirusBoxes().size());

        assertNull(infectionService.infectCity(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE))));
        assertEquals(3, atlanta.getVirusBoxes().size());

        assertEquals(VirusType.BLUE, infectionService.infectCity(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE))));
        assertEquals(3, atlanta.getVirusBoxes().size());

    }

    @Test
    void canCityBeInfected_virusIsEradicated() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        List<Virus> virusList = board.getVirusList();
        Virus blueVirus = virusList.stream().filter(virus -> BLUE.equals(virus.getVirusType())).findFirst().orElse(null);
        blueVirus.virusHasBeenEradicated();

        assertFalse(infectionService.canCityBeInfected(paris, virusList, board.getPlayers()));
    }

    @Test
    void spreadOutbreak() {
    }


}
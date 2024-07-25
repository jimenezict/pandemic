package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.model.board.Board;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.MedicPlayer;
import com.dataontheroad.pandemic.model.player.QuarantinePlayer;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.model.board.BoardFactory.createBaseBoard;
import static com.dataontheroad.pandemic.model.city.CityEnum.*;
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
    void infectCity() {
        City atlanta = board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE));
        atlanta.setVirusBoxes(new ArrayList<>());
        infectionService.infectCityAndReturnCityVirusTypeIfOverpassOutbreak(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE)));
        assertEquals(1, atlanta.getVirusBoxes().size());

        assertNull(infectionService.infectCityAndReturnCityVirusTypeIfOverpassOutbreak(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE))));
        assertEquals(2, atlanta.getVirusBoxes().size());

        assertNull(infectionService.infectCityAndReturnCityVirusTypeIfOverpassOutbreak(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE))));
        assertEquals(3, atlanta.getVirusBoxes().size());

        assertEquals(VirusType.BLUE, infectionService.infectCityAndReturnCityVirusTypeIfOverpassOutbreak(board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE))));
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
    void canCityBeInfected_virusIsCuredAndMedicIsNotOnTheCity_propagate() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        board.getPlayers().clear();
        MedicPlayer medicPlayer = new MedicPlayer();
        medicPlayer.setCity(board.getCityFromBoardList(new City(WASHINGTON.cityName, BLUE)));
        board.getPlayers().add(medicPlayer);
        List<Virus> virusList = board.getVirusList();
        Virus blueVirus = virusList.stream().filter(virus -> BLUE.equals(virus.getVirusType())).findFirst().orElse(null);
        blueVirus.cureHasBeenDiscovered();
        assertTrue(infectionService.canCityBeInfected(paris, virusList, board.getPlayers()));
    }

    @Test
    void canCityBeInfected_virusIsCuredAndMedicIsOnTheCity_notPropagate() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        board.getPlayers().clear();
        MedicPlayer medicPlayer = new MedicPlayer();
        medicPlayer.setCity(paris);
        board.getPlayers().add(medicPlayer);
        List<Virus> virusList = board.getVirusList();
        Virus blueVirus = virusList.stream().filter(virus -> BLUE.equals(virus.getVirusType())).findFirst().orElse(null);
        blueVirus.cureHasBeenDiscovered();
        assertFalse(infectionService.canCityBeInfected(paris, virusList, board.getPlayers()));
    }

    @Test
    void canCityBeInfected_virusIsNotCuredAndMedicIsOnTheCity_propagate() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        board.getPlayers().clear();
        MedicPlayer medicPlayer = new MedicPlayer();
        medicPlayer.setCity(board.getCityFromBoardList(new City(WASHINGTON.cityName, BLUE)));
        board.getPlayers().add(medicPlayer);
        List<Virus> virusList = board.getVirusList();
        assertTrue(infectionService.canCityBeInfected(paris, virusList, board.getPlayers()));
    }

    @Test
    void canCityBeInfected_virusIsNotCuredAndMedicIsNotOnTheCity_propagate() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        board.getPlayers().clear();
        MedicPlayer medicPlayer = new MedicPlayer();
        medicPlayer.setCity(board.getCityFromBoardList(new City(WASHINGTON.cityName, BLUE)));
        board.getPlayers().add(medicPlayer);
        List<Virus> virusList = board.getVirusList();
        assertTrue(infectionService.canCityBeInfected(paris, virusList, board.getPlayers()));
    }

    @Test
    void canCityBeInfected_virusIsNotCuredAndQuarantineIsOnTheCity_notPropagate() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        board.getPlayers().clear();
        QuarantinePlayer quarantinePlayer = new QuarantinePlayer();
        quarantinePlayer.setCity(paris);
        board.getPlayers().add(quarantinePlayer);
        assertFalse(infectionService.canCityBeInfected(paris, board.getVirusList(), board.getPlayers()));
    }

    @Test
    void canCityBeInfected_virusIsNotCuredAndQuarantineIsOnNearCity_notPropagate() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        City madrid = board.getCityFromBoardList(new City(MADRID.cityName, BLUE));
        board.getPlayers().clear();
        QuarantinePlayer quarantinePlayer = new QuarantinePlayer();
        quarantinePlayer.setCity(madrid);
        board.getPlayers().add(quarantinePlayer);
        assertFalse(infectionService.canCityBeInfected(paris, board.getVirusList(), board.getPlayers()));
    }

    @Test
    void canCityBeInfected_virusIsNotCuredAndQuarantineIsFarCity_Propagate() {
        City paris = board.getCityFromBoardList(new City(PARIS.cityName, BLUE));
        City atlanta = board.getCityFromBoardList(new City(ATLANTA.cityName, BLUE));
        board.getPlayers().clear();
        QuarantinePlayer quarantinePlayer = new QuarantinePlayer();
        quarantinePlayer.setCity(atlanta);
        board.getPlayers().add(quarantinePlayer);
        assertTrue(infectionService.canCityBeInfected(paris, board.getVirusList(), board.getPlayers()));
    }


}
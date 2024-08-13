package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.*;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.game.TurnServiceHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnServiceHelperTest {


    private final City newyork = new City("New York", VirusType.BLUE);
    private final City essen = new City("Essen", VirusType.BLUE);

    @Test
    void getOtherPlayersOnTheCity_shouldReturn2() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        List<Player> playerList = getOtherPlayersOnTheCity(gameDTO);
        assertEquals(2, playerList.size());
    }

    @Test
    void getCitiesWithResearchCenter_3researchcenters() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        gameDTO.getBoard().getCityFromBoardList(newyork).setHasCenter(true);
        gameDTO.getBoard().getCityFromBoardList(essen).setHasCenter(true);

        List<City> citiesWithResearchCenter = getCitiesWithResearchCenter(gameDTO);
        assertEquals(3, citiesWithResearchCenter.size());
    }

    @Test
    void getNextActivePlayer_onGameOfFourPlayers() {
        Player scientist = new ScientistPlayer();
        Player operations = new OperationsPlayer();
        Player medic = new MedicPlayer();
        Player contingency = new ContingencyPlayer();
        List<Player> playerList = Arrays.asList(scientist, operations, medic, contingency);
        assertEquals(operations, getNextActivePlayer(playerList, scientist));
        assertEquals(medic, getNextActivePlayer(playerList, operations));
        assertEquals(contingency, getNextActivePlayer(playerList, medic));
        assertEquals(scientist, getNextActivePlayer(playerList, contingency));
    }

    @Test
    void getNextActivePlayer_onGameOfTwoPlayers() {
        Player scientist = new ScientistPlayer();
        Player operations = new OperationsPlayer();
        List<Player> playerList = Arrays.asList(scientist, operations);
        assertEquals(operations, getNextActivePlayer(playerList, scientist));
        assertEquals(scientist, getNextActivePlayer(playerList, operations));
        assertEquals(operations, getNextActivePlayer(playerList, scientist));
        assertEquals(scientist, getNextActivePlayer(playerList, operations));
    }


}
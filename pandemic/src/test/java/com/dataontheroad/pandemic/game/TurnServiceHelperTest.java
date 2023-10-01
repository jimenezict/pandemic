package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.cards.model.EpidemicCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.*;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_EMPTY_DECK;
import static com.dataontheroad.pandemic.game.TurnServiceHelper.*;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;

class TurnServiceHelperTest {


    private City newyork = new City("New York", VirusType.BLUE);
    private City essen = new City("Essen", VirusType.BLUE);

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

    @Test
    void playerGetNewCardsIfIsNotEpidemic_isCityCard() throws EndOfGameException {
        Player scientist = new ScientistPlayer();
        PlayerQueue playerQueue = new PlayerQueue(Arrays.asList(createCityCard(newyork)));
        assertTrue(playerGetNewCardsIfIsNotEpidemic(playerQueue, scientist));
        assertEquals(1, scientist.getListCard().size());
    }

    @Test
    void playerGetNewCardsIfIsNotEpidemic_isEpidemic() throws EndOfGameException {
        Player scientist = new ScientistPlayer();
        PlayerQueue playerQueue = new PlayerQueue(Arrays.asList(new EpidemicCard()));
        assertFalse(playerGetNewCardsIfIsNotEpidemic(playerQueue, scientist));
        assertTrue(scientist.getListCard().isEmpty());
    }

    @Test
    void playerGetNewCardsIfIsNotEpidemic_emptyDeck() {
        Player scientist = new ScientistPlayer();
        PlayerQueue playerQueue = new PlayerQueue(new ArrayList<>());

        EndOfGameException exception =
                assertThrows(EndOfGameException.class,
                        () -> playerGetNewCardsIfIsNotEpidemic(playerQueue, scientist));

        assertTrue(exception.getMessage().contains(END_OF_GAME_EMPTY_DECK));
    }

}
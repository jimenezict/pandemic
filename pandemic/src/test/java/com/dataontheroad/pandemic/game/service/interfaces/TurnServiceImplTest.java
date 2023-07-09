package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.*;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl.getCitiesWithResearchCenter;
import static com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl.getOtherPlayersOnTheCity;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnServiceImplTest {
    @InjectMocks
    TurnServiceImpl turnService;

    @Mock
    GamePersistenceOnHashMap gamePersistence;

    private static UUID uuid = randomUUID();

    private City newyork = new City("New York", VirusType.BLUE);
    private City essen = new City("Essen", VirusType.BLUE);

    @Test
    void getTurnServiceInformation_returnNull() {
        when(gamePersistence.getGameById(any())).thenReturn(null);
        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(uuid);
        assertNull(turnResponseDTO);
    }

    @Test
    void getTurnServiceInformation_returnGameDTO() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);

        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(uuid);
        assertEquals(4, turnResponseDTO.getMissingTurns());
        assertEquals(4, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getName(), turnResponseDTO.getActivePlayer().getName());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getCity().getName(), turnResponseDTO.getActivePlayer().getLocation().getName());
        assertFalse(turnResponseDTO.getActionList().isEmpty());
        assertFalse(turnResponseDTO.getActivePlayer().getListCard().isEmpty());
    }

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
    void executeAction() throws Exception {
        GameDTO gameDTO = new GameDTO(2);
        Player originalActivePlayer = gameDTO.getTurnInformation().getActivePlayer();

        City originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), 0);
        City finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(3, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());

        originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), 0);
        finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(2, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());

        originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), 0);
        finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(1, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());

        originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), 0);
        finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(4, gameDTO.getTurnInformation().getMissingTurns());
        assertNotEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());
        verify(gamePersistence).insertOrUpdateGame(gameDTO);
    }


    @Test
    void getNextActivePlayer() {
        Player scientist = new ScientistPlayer();
        Player operations = new OperationsPlayer();
        Player medic = new MedicPlayer();
        Player contingency = new ContingencyPlayer();
        List<Player> playerList = Arrays.asList(scientist, operations, medic, contingency);
        assertEquals(operations, turnService.getNextActivePlayer(playerList, scientist));
        assertEquals(medic, turnService.getNextActivePlayer(playerList, operations));
        assertEquals(contingency, turnService.getNextActivePlayer(playerList, medic));
        assertEquals(scientist, turnService.getNextActivePlayer(playerList, contingency));
    }
}
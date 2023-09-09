package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.GAME_NOT_FOUND;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnServiceHelperImplTest {
    @InjectMocks
    TurnServiceImpl turnService;

    @Mock
    GamePersistenceOnHashMap gamePersistence;

    private static UUID uuid = randomUUID();


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
    void executeAction_returnNullValue() throws Exception {
        GameDTO gameDTO = new GameDTO(2);
        when(gamePersistence.getGameById(any())).thenReturn(null);

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.executeAction(gameDTO.getUuid(), 0));

        assertTrue(exception.getMessage().contains(GAME_NOT_FOUND));
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

}
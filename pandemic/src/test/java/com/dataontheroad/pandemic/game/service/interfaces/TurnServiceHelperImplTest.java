package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DriveFerryAction;
import com.dataontheroad.pandemic.actions.action_factory.FlyCharterAction;
import com.dataontheroad.pandemic.actions.action_factory.FlyDirectAction;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
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

import java.util.HashMap;
import java.util.UUID;

import static com.dataontheroad.pandemic.actions.ActionsType.DRIVEFERRY;
import static com.dataontheroad.pandemic.actions.ActionsType.FLYCHARTER;
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
    void getSelectedAction_returnNullValue() throws Exception {
        GameDTO gameDTO = new GameDTO(2);
        when(gamePersistence.getGameById(any())).thenReturn(null);

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.getSelectedAction(gameDTO.getUuid(), 0));

        assertTrue(exception.getMessage().contains(GAME_NOT_FOUND));
    }
    @Test
    void executeAction() throws Exception {
        GameDTO gameDTO = new GameDTO(2);
        Player originalActivePlayer = gameDTO.getTurnInformation().getActivePlayer();

        Action actionTest = new DriveFerryAction(originalActivePlayer, originalActivePlayer.getCity().getNodeCityConnection().get(0));
        City originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), actionTest);
        City finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(3, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());

        originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        actionTest = new DriveFerryAction(originalActivePlayer, originalCity.getNodeCityConnection().get(0));
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), actionTest);
        finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(2, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());

        originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        actionTest = new DriveFerryAction(originalActivePlayer, originalCity.getNodeCityConnection().get(0));
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), actionTest);
        finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(1, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());

        originalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        actionTest = new DriveFerryAction(originalActivePlayer, originalCity.getNodeCityConnection().get(0));
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);
        turnService.executeAction(gameDTO.getUuid(), actionTest);
        finalCity = gameDTO.getTurnInformation().getActivePlayer().getCity();
        assertNotEquals(originalCity.getName(), finalCity.getName());
        assertEquals(4, gameDTO.getTurnInformation().getMissingTurns());
        assertNotEquals(originalActivePlayer, gameDTO.getTurnInformation().getActivePlayer());
        verify(gamePersistence).insertOrUpdateGame(gameDTO);
    }

    @Test
    void actionFormatValidation_destinationCity_isValidFlyCharter() throws Exception {
        String city = "Paris";
        GameDTO gameDTO = new GameDTO(3);
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);

        Action action = new FlyCharterAction(gameDTO.getTurnInformation().getActivePlayer());
        HashMap<String, String> additionalFields = new HashMap<>();
        additionalFields.put("destination", city);

        turnService.actionFormatValidation(uuid, action, additionalFields);

        assertEquals(city, ((FlyCharterAction) action).getDestination().getName());
        assertEquals(FLYCHARTER, action.getActionsType());
    }

    @Test
    void actionFormatValidation_destinationCity_isInvalidFlyCharter() throws Exception {
        String city = "SVH";
        GameDTO gameDTO = new GameDTO(3);
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);

        Action action = new FlyCharterAction(gameDTO.getTurnInformation().getActivePlayer());
        HashMap<String, String> additionalFields = new HashMap<>();
        additionalFields.put("destination", city);

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.actionFormatValidation(uuid, action, additionalFields));

        assertTrue(exception.getMessage().contains("destination is not a valid city on action " + FLYCHARTER.name()));
    }

    @Test
    void actionFormatValidation_destinationCityIsMissing() throws Exception {
        String city = "SVH";
        GameDTO gameDTO = new GameDTO(3);
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);

        Action action = new FlyCharterAction(gameDTO.getTurnInformation().getActivePlayer());
        HashMap<String, String> additionalFields = new HashMap<>();

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.actionFormatValidation(uuid, action, additionalFields));

        assertTrue(exception.getMessage().contains("destination field is mandatory when action is " + FLYCHARTER.name()));
    }

}
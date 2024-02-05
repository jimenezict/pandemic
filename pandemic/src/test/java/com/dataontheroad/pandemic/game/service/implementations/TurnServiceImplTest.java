package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DriveFerryAction;
import com.dataontheroad.pandemic.actions.action_factory.FlyCharterAction;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.FlyFromResearchCenterAnywhereAction;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.turn.TurnExecuteDTO;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.service.implementations.EndOfTurnServiceImpl;
import com.dataontheroad.pandemic.game.service.implementations.InfectionServiceImpl;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.UUID;

import static com.dataontheroad.pandemic.actions.ActionsType.FLYCHARTER;
import static com.dataontheroad.pandemic.actions.ActionsType.OPERATION_FLY;
import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TurnServiceImplTest {
    @InjectMocks
    TurnServiceImpl turnService;

    @Mock
    GamePersistenceOnHashMap gamePersistence;

    @Mock
    InfectionServiceImpl infectionService;

    @Mock
    EndOfTurnServiceImpl endOfTurnService;

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
    }

    @Test
    void actionFormatValidation_destinationCity_isValidFlyCharter() throws Exception {
        String city = "Paris";
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyCharterAction(gameDTO.getTurnInformation().getActivePlayer());
        HashMap<String, String> additionalFields = new HashMap<>();
        additionalFields.put(ADDITIONAL_FIELD_DESTINATION, city);

        turnService.validateActionFormat(turnExecuteDTO, action, additionalFields);

        assertEquals(city, ((FlyCharterAction) action).getDestination().getName());
        assertEquals(FLYCHARTER, action.getActionsType());
    }

    @Test
    void actionFormatValidation_destinationCity_isValidOperationPlayerFlyFromResearchCenterAnywhere() throws Exception {
        String city = "Paris";
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyFromResearchCenterAnywhereAction(new OperationsPlayer());
        HashMap<String, String> additionalFields = new HashMap<>();
        additionalFields.put(ADDITIONAL_FIELD_DESTINATION, city);

        turnService.validateActionFormat(turnExecuteDTO, action, additionalFields);

        assertEquals(city, ((FlyFromResearchCenterAnywhereAction) action).getDestination().getName());
        assertEquals(OPERATION_FLY, action.getActionsType());
    }

    @Test
    void actionFormatValidation_destinationCity_isInvalidFlyCharter() throws Exception {
        String city = "SVH";
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyCharterAction(gameDTO.getTurnInformation().getActivePlayer());
        HashMap<String, String> additionalFields = new HashMap<>();
        additionalFields.put(ADDITIONAL_FIELD_DESTINATION, city);

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.validateActionFormat(turnExecuteDTO, action, additionalFields));

        assertTrue(exception.getMessage().contains(TURN_WRONG_FLYCHARTER_INVALID_DESTINATION_CITY));
    }

    @Test
    void actionFormatValidation_destinationCity_isInvalidOperationPlayerFlyFromResearchCenterAnywhere() throws Exception {
        String city = "SVH";
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyFromResearchCenterAnywhereAction(new OperationsPlayer());
        HashMap<String, String> additionalFields = new HashMap<>();
        additionalFields.put(ADDITIONAL_FIELD_DESTINATION, city);

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.validateActionFormat(turnExecuteDTO, action, additionalFields));

        assertTrue(exception.getMessage().contains(TURN_WRONG_OPERATION_INVALID_DESTINATION_CITY));
    }

    @Test
    void actionFormatValidation_destinationCityIsMissing_isInvalidFlyCharter() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyCharterAction(gameDTO.getTurnInformation().getActivePlayer());
        HashMap<String, String> additionalFields = new HashMap<>();

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.validateActionFormat(turnExecuteDTO, action, additionalFields));

        assertTrue(exception.getMessage().contains(TURN_WRONG_FLYCHARTER_DESTINATION_FIELD));
    }

    @Test
    void actionFormatValidation_destinationCityIsMissing_isInvalidOperationPlayerFlyFromResearchCenterAnywhere() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyFromResearchCenterAnywhereAction(new OperationsPlayer());
        HashMap<String, String> additionalFields = new HashMap<>();

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.validateActionFormat(turnExecuteDTO, action, additionalFields));

        assertTrue(exception.getMessage().contains(TURN_WRONG_OPERATION_DESTINATION_FIELD));
    }

    @Test
    void actionFormatValidation_additionalFieldsIsMissing_isInvalidFlyCharter() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyCharterAction(gameDTO.getTurnInformation().getActivePlayer());
        HashMap<String, String> additionalFields = new HashMap<>();

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.validateActionFormat(turnExecuteDTO, action, null));

        assertTrue(exception.getMessage().contains(TURN_WRONG_FLYCHARTER_DESTINATION_FIELD));
    }

    @Test
    void actionFormatValidation_additionalFieldsIsMissing_isInvalidOperationPlayerFlyFromResearchCenterAnywhere() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        TurnExecuteDTO turnExecuteDTO = new TurnExecuteDTO(gameDTO);

        Action action = new FlyFromResearchCenterAnywhereAction(new OperationsPlayer());
        HashMap<String, String> additionalFields = new HashMap<>();

        GameExecutionException exception =
                assertThrows(GameExecutionException.class,
                        () -> turnService.validateActionFormat(turnExecuteDTO, action, null));

        assertTrue(exception.getMessage().contains(TURN_WRONG_OPERATION_DESTINATION_FIELD));
    }
}
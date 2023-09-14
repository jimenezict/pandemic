package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyCharterAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.turn.ExecutionSuccessResponse;
import com.dataontheroad.pandemic.game.api.model.turn.TurnRequestDTO;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.ScientistPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.SCIENTIST_NAME;
import static com.dataontheroad.pandemic.game.modelBuilder.buildTurnResponseDTOWithActionList;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TurnEndPoint.class)
class TurnEndPointExecuteMockMvcTest {

        @Autowired
        private MockMvc mvc;
        @Autowired
        private ObjectMapper objectMapper;
        private City atlanta = new City("Atlanta", VirusType.BLUE);

        @MockBean
        TurnServiceImpl turnService;

        private static UUID uuid = randomUUID();

        @Test
        void getTurnExecute_whenGameDoNotExists() throws Exception {
                when(turnService.getTurnServiceInformation(any())).thenReturn(null);

                TurnRequestDTO turnRequestDTO =
                        new TurnRequestDTO(uuid, SCIENTIST_NAME, 5, null);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .post("/turn/execute")
                                .content(objectMapper.writeValueAsString(turnRequestDTO))
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                MvcResult result = resultActions.andReturn();
                String contentAsString = result.getResponse().getContentAsString();

                ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
                assertEquals(TURN_ENDPOINT_NAME, response.getEndpoint());
                assertEquals(uuid, response.getGameID());
                assertEquals(GAME_NOT_FOUND, response.getMessage());
        }

        @Test
        void getTurnExecute_whenPlayerIsNotActive() throws Exception {
                when(turnService.getTurnServiceInformation(any()))
                        .thenReturn(buildTurnResponseDTOWithActionList(new OperationsPlayer()));

                TurnRequestDTO turnRequestDTO =
                        new TurnRequestDTO(uuid, SCIENTIST_NAME, 5, null);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .post("/turn/execute")
                                .content(objectMapper.writeValueAsString(turnRequestDTO))
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                MvcResult result = resultActions.andReturn();
                String contentAsString = result.getResponse().getContentAsString();

                ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
                assertEquals(TURN_ENDPOINT_NAME, response.getEndpoint());
                assertEquals(uuid, response.getGameID());
                assertEquals(TURN_WRONG_PLAYER, response.getMessage());
        }

        @Test
        void getTurnExecute_whenActionIsNotValid() throws Exception {
                when(turnService.getTurnServiceInformation(any()))
                        .thenReturn(buildTurnResponseDTOWithActionList(new ScientistPlayer()));

                TurnRequestDTO turnRequestDTO =
                        new TurnRequestDTO(uuid, SCIENTIST_NAME, 10, null);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .post("/turn/execute")
                                .content(objectMapper.writeValueAsString(turnRequestDTO))
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                MvcResult result = resultActions.andReturn();
                String contentAsString = result.getResponse().getContentAsString();

                ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
                assertEquals(TURN_ENDPOINT_NAME, response.getEndpoint());
                assertEquals(uuid, response.getGameID());
                assertEquals(TURN_WRONG_ACTION, response.getMessage());
        }

        @Test
        void getTurnExecute_success() throws Exception {
                Player activePlayer = new ScientistPlayer();
                when(turnService.getTurnServiceInformation(any()))
                        .thenReturn(buildTurnResponseDTOWithActionList(activePlayer));
                when(turnService.executeAction(any(), any())).thenReturn(new TurnInformation(activePlayer));


                TurnRequestDTO turnRequestDTO =
                        new TurnRequestDTO(uuid, SCIENTIST_NAME, 2, null);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .post("/turn/execute")
                                .content(objectMapper.writeValueAsString(turnRequestDTO))
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isOk());

                MvcResult result = resultActions.andReturn();
                String contentAsString = result.getResponse().getContentAsString();

                ExecutionSuccessResponse response = objectMapper.readValue(contentAsString, ExecutionSuccessResponse.class);
                assertEquals(TURN_ENDPOINT_NAME, response.getEndpoint());
                assertEquals(uuid, response.getGameID());
                assertEquals(SUCCESS_ACTION, response.getMessage());
                assertEquals(SCIENTIST_NAME, response.getPlayerName());
        }

        @Test
        void getTurnExecute_executeThrowException_actionIsNotValid() throws Exception {
                when(turnService.getTurnServiceInformation(any()))
                        .thenReturn(buildTurnResponseDTOWithActionList(new ScientistPlayer()));

                doThrow(new ActionException(ActionsType.DRIVEFERRYDISPATCHER, DRIVEFERRY_ERROR_NO_CONNECTION)).
                        when(turnService).executeAction(any(UUID.class), any());

                TurnRequestDTO turnRequestDTO =
                        new TurnRequestDTO(uuid, SCIENTIST_NAME, 2, null);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .post("/turn/execute")
                                .content(objectMapper.writeValueAsString(turnRequestDTO))
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                MvcResult result = resultActions.andReturn();
                String contentAsString = result.getResponse().getContentAsString();

                ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
                assertEquals(TURN_ENDPOINT_NAME, response.getEndpoint());
                assertEquals(uuid, response.getGameID());
                assertTrue(response.getMessage().contains("Action Move to a city connected by a white line ordered by the Dispatcher got Exception with message: Destinition is not available for the origin city"));
        }

        @Test
        void getTurnExecute_executeThrowException_gameDoNotExists() throws Exception {
                when(turnService.getTurnServiceInformation(any()))
                        .thenReturn(buildTurnResponseDTOWithActionList(new ScientistPlayer()));

                doThrow(new GameExecutionException(GAME_NOT_FOUND)).
                        when(turnService).executeAction(any(UUID.class), any());

                TurnRequestDTO turnRequestDTO =
                        new TurnRequestDTO(uuid, SCIENTIST_NAME, 0, null);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .post("/turn/execute")
                                .content(objectMapper.writeValueAsString(turnRequestDTO))
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                MvcResult result = resultActions.andReturn();
                String contentAsString = result.getResponse().getContentAsString();

                ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
                assertEquals(TURN_ENDPOINT_NAME, response.getEndpoint());
                assertEquals(uuid, response.getGameID());
                assertTrue(response.getMessage().contains(GAME_NOT_FOUND));
        }

        @Test
        void getTurnExecute_executeFlyCharter_withDestinationExtraValue() throws Exception {
                Player activePlayer = new ScientistPlayer();
                when(turnService.getTurnServiceInformation(any()))
                        .thenReturn(buildTurnResponseDTOWithActionList(activePlayer));
                when(turnService.executeAction(any(), any())).thenReturn(new TurnInformation(activePlayer));
                FlyCharterAction flyCharterAction = new FlyCharterAction(activePlayer);
                when(turnService.getSelectedAction(any(), anyInt())).thenReturn(flyCharterAction);
                HashMap<String, String> additionalFields = new HashMap<>();
                additionalFields.put(ADDITIONAL_FIELD_DESTINATION, "Paris");

                TurnRequestDTO turnRequestDTO =
                        new TurnRequestDTO(uuid, SCIENTIST_NAME, 0, additionalFields);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .post("/turn/execute")
                                .content(objectMapper.writeValueAsString(turnRequestDTO))
                                .contentType("application/json;charset=UTF-8"))
                        .andDo(print())
                        .andExpect(status().isOk());
        }
}


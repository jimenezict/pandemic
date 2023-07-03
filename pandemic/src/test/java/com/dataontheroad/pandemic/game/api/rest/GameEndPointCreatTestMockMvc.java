package com.dataontheroad.pandemic.game.api.rest;


import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.commons.SuccessResponse;
import com.dataontheroad.pandemic.game.service.implementations.GameServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameEndPoint.class)
class GameEndPointCreatTestMockMvc {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    GameServiceImpl gameService;

    private static UUID uuid = randomUUID();

    @Test
    void create_whenNumPandemicIsInvalid7() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenReturn(uuid);
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(2,7))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
        assertEquals(GAME_ENDPOINT_NAME, response.getEndpoint());
        assertNull(response.getGameID());
        assertEquals(WRONG_EPIDEMIC_CARDS, response.getMessage());
    }

    @Test
    void create_whenNumPlayersIsInvalid1() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenThrow(new GameExecutionException("lorem ipsum"));
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(1,4))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
        assertEquals(GAME_ENDPOINT_NAME, response.getEndpoint());
        assertNull(response.getGameID());
        assertEquals(WRONG_PLAYERS, response.getMessage());
    }

    @Test
    void create_whenNumPlayersIsInvalid5() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenThrow(new GameExecutionException("lorem ipsum"));
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(5,4))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
        assertEquals(GAME_ENDPOINT_NAME, response.getEndpoint());
        assertNull(response.getGameID());
        assertEquals(WRONG_PLAYERS, response.getMessage());
    }

    @Test
    void create_success() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenReturn(uuid);
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(3,4))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        SuccessResponse response = objectMapper.readValue(contentAsString, SuccessResponse.class);
        assertEquals(GAME_ENDPOINT_NAME, response.getEndpoint());
        assertEquals(uuid, response.getGameID());
        assertEquals(SUCCESS_GAME, response.getMessage());
    }


    private String getCreateURL(int numPlayers, int numPandemics) {
        return "/game/create/players/" + numPlayers + "/pandemic/" + numPandemics;
    }

}
package com.dataontheroad.pandemic.game.api.rest;


import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
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

import static com.dataontheroad.pandemic.constants.LiteralGame.GAME_ENDPOINT_NAME;
import static com.dataontheroad.pandemic.constants.LiteralGame.GAME_NOT_FOUND;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameEndPoint.class)
class GameEndPointReadMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    GameServiceImpl gameService;

    private static final UUID uuid = randomUUID();

    @Test
    void readGame_whenGameDoNotExists() throws Exception {
        when(gameService.getGameById(any())).thenReturn(null);

        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .get("/game/read/"+uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        ErrorResponse response = objectMapper.readValue(contentAsString, ErrorResponse.class);
        assertEquals(GAME_ENDPOINT_NAME, response.getEndpoint());
        assertEquals(uuid, response.getGameID());
        assertEquals(GAME_NOT_FOUND, response.getMessage());
    }

    @Test
    void readGame_whenParameterIsNotUUID() throws Exception {
        when(gameService.getGameById(any())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders
                        .get("/game/read/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void readGame_whenReturnsGame() throws Exception {
        GameResponseDTO gameResponseDTO = new GameResponseDTO(uuid);
        when(gameService.getGameById(any())).thenReturn(gameResponseDTO);

        mvc.perform(MockMvcRequestBuilders
                        .get("/game/read/"+uuid)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
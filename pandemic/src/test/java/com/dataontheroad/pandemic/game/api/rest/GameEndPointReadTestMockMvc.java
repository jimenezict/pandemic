package com.dataontheroad.pandemic.game.api.rest;


import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.game.service.implementations.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameEndPoint.class)
class GameEndPointReadTestMockMvc {

    @Autowired
    private MockMvc mvc;

    @MockBean
    GameServiceImpl gameService;

    private static UUID uuid = randomUUID();

    @Test
    void readGame_whenGameDoNotExists() throws Exception {
        when(gameService.getGameById(any())).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders
                .get("/game/read/"+uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
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
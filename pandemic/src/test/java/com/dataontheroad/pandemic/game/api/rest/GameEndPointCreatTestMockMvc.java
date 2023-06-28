package com.dataontheroad.pandemic.game.api.rest;


import com.dataontheroad.pandemic.exceptions.GameExecutionException;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameEndPoint.class)
class GameEndPointCreatTestMockMvc {

    @Autowired
    private MockMvc mvc;

    @MockBean
    GameServiceImpl gameService;

    private static UUID uuid = randomUUID();

    @Test
    void create_whenNumPandemicIsInvalid1() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenReturn(uuid);
        mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(2,7))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_whenNumPlayersIsInvalid1() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenThrow(new GameExecutionException("lorem ipsum"));
        mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(1,4))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_whenNumPlayersIsInvalid5() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenThrow(new GameExecutionException("lorem ipsum"));
        mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(5,4))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_success() throws GameExecutionException, Exception {
        when(gameService.createGame(anyInt(), anyInt())).thenReturn(uuid);
        mvc.perform(MockMvcRequestBuilders
                        .get(getCreateURL(3,4))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    private String getCreateURL(int numPlayers, int numPandemics) {
        return "/game/create/players/" + numPlayers + "/pandemic/" + numPandemics;
    }

}
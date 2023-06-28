package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
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
@WebMvcTest(TurnEndPoint.class)
class TurnEndPointTestMockMvc {

        @Autowired
        private MockMvc mvc;

        @MockBean
        TurnServiceImpl turnService;

        private static UUID uuid = randomUUID();

        @Test
        void getTurn_whenGameDoNotExists() throws Exception {
                when(turnService.getTurnServiceInformation(any())).thenReturn(null);

                mvc.perform(MockMvcRequestBuilders
                                .get("/turn/status/"+uuid)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNotFound());
        }

        /*
        @Test
        void getTurn_success() throws Exception {
                TurnInformation turnInformation = new TurnInformation(new Player());
                TurnResponseDTO turnResponseDTO = new TurnResponseDTO();
                turnResponseDTO.setTurnInformation(turnInformation, new ArrayList<>());

                when(turnService.getTurnServiceInformation(any())).thenReturn(turnResponseDTO);

                mvc.perform(MockMvcRequestBuilders
                                .get("/turn/status/"+uuid)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk());
        }
        */

}


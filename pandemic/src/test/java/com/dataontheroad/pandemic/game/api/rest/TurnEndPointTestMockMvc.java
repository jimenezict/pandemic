package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
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

import java.util.ArrayList;
import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TurnEndPoint.class)
class TurnEndPointTestMockMvc {

        @Autowired
        private MockMvc mvc;
        @Autowired
        private ObjectMapper objectMapper;
        private City atlanta = new City("Atlanta", VirusType.BLUE);

        @MockBean
        TurnServiceImpl turnService;

        private static UUID uuid = randomUUID();

        @Test
        void getTurn_whenGameDoNotExists() throws Exception {
                when(turnService.getTurnServiceInformation(any())).thenReturn(null);

                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .get("/turn/status/"+uuid)
                                .accept(MediaType.APPLICATION_JSON))
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
        void getTurn_success() throws Exception {
                TurnInformation turnInformation = new TurnInformation(new Player(atlanta));
                TurnResponseDTO turnResponseDTO = new TurnResponseDTO();
                turnResponseDTO.setTurnInformation(turnInformation, new ArrayList<>());

                when(turnService.getTurnServiceInformation(any())).thenReturn(turnResponseDTO);

                mvc.perform(MockMvcRequestBuilders
                                .get("/turn/status/"+uuid)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk());
        }


}


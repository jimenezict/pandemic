package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static java.util.Objects.isNull;

@RestController
public class TurnEndPoint {


    @Autowired
    TurnServiceImpl turnService;

    @GetMapping("/turn/status/{gameId}")
    ResponseEntity getTurn(@PathVariable UUID gameId) {
        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(gameId);
        if(isNull(turnResponseDTO)) {
            ErrorResponse errorResponse = new ErrorResponse(TURN_ENDPOINT_NAME, gameId, GAME_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok().body(turnResponseDTO);
    }

}

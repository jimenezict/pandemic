package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
public class TurnEndPoint {

    @Autowired
    TurnServiceImpl turnService;

    @GetMapping("/turn/status/{gameId}")
    ResponseEntity getTurn(@PathVariable UUID gameId) {
        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(gameId);
        if(isNull(turnResponseDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameId + ": Not Found");
        }
        return ResponseEntity.ok().body(turnResponseDTO);
    }

}

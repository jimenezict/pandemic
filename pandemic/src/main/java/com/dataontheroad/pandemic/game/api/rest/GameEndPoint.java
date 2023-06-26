package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.game.service.interfaces.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
public class GameEndPoint {

    @Autowired
    GameServiceImpl gameService;

    @GetMapping("/game/create/players/{numPlayers}/pandemic/{numPandemics}")
    ResponseEntity<?> createGame(@PathVariable int numPlayers, @PathVariable int numPandemics) {
        UUID uuid;

        if(numPandemics < 0 || numPandemics > 6) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating game, epidemic cards should be between 0 and 6");
        }

        try {
            uuid = gameService.createGame(numPandemics, numPlayers);
        } catch (GameExecutionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating game, players should be between 2 and 4");
        }

        return ResponseEntity.ok().body("created game with id " + uuid);
    }

    @GetMapping("/game/read/{gameId}")
    ResponseEntity readGame(@PathVariable UUID gameId) {
        GameResponseDTO gameResponseDTO = gameService.getGameById(gameId);
        if(isNull(gameResponseDTO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(gameResponseDTO);
    }

}

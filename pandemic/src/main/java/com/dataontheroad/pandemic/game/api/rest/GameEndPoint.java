package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.game.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GameEndPoint {

    @Autowired
    GameServiceImpl gameService;

    @GetMapping("/game/create/players/{numPlayers}/pandemic/{numPandemics}")
    ResponseEntity<?> createGame(@PathVariable int numPlayers, @PathVariable int numPandemics) {
        UUID uuid;

        try {
            uuid = gameService.createGame(numPandemics, numPlayers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating game, players should be between 2 and 4");
        }
        return ResponseEntity.ok().body("created game with id " + uuid);
    }

    @GetMapping("/game/read/{gameId}")
    ResponseEntity readGame(@PathVariable UUID gameId) {
        return ResponseEntity.ok().body(gameService.getGameById(gameId));
    }

}

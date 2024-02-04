package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.commons.SuccessResponse;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.game.service.implementations.GameServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static java.util.Objects.isNull;

@RestController
public class GameEndPoint {

    private final GameServiceImpl gameService;

    public GameEndPoint(GameServiceImpl gameService) {
        this.gameService = gameService;
    }


    @GetMapping("/game/create/players/{numPlayers}/pandemic/{numPandemics}")
    ResponseEntity createGame(@PathVariable int numPlayers, @PathVariable int numPandemics) {
        UUID uuid;

        if(numPandemics < 0 || numPandemics > 6) {
            ErrorResponse errorResponse = new ErrorResponse(GAME_ENDPOINT_NAME, null, WRONG_EPIDEMIC_CARDS);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            uuid = gameService.createGame(numPandemics, numPlayers);
        } catch (GameExecutionException e) {
            ErrorResponse errorResponse = new ErrorResponse(GAME_ENDPOINT_NAME, null, WRONG_PLAYERS);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        SuccessResponse successResponse = new SuccessResponse(GAME_ENDPOINT_NAME, uuid, SUCCESS_GAME);
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/game/read/{gameId}")
    ResponseEntity readGame(@PathVariable UUID gameId) {
        GameResponseDTO gameResponseDTO = gameService.getGameById(gameId);
        if(isNull(gameResponseDTO)) {
            ErrorResponse errorResponse = new ErrorResponse(GAME_ENDPOINT_NAME, gameId, GAME_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok().body(gameResponseDTO);
    }

}

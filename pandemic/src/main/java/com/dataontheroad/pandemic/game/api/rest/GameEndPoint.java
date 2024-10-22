package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.commons.SuccessResponse;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.game.service.implementations.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static java.util.Objects.isNull;



@RestController
public class GameEndPoint {

    private final GameServiceImpl gameService;

    final Logger logger = LoggerFactory.getLogger(GameEndPoint.class);

    public GameEndPoint(GameServiceImpl gameService) {
        this.gameService = gameService;
    }


    @GetMapping("/game/create/players/{numPlayers}/pandemic/{numPandemics}")
    ResponseEntity createGame(@PathVariable int numPlayers, @PathVariable int numPandemics) {
        UUID uuid;
        logger.info("Starting the creation of a game with {} players and {} pandemic cards", numPlayers, numPandemics);

        if(numPandemics < 0 || numPandemics > 6) {
            ErrorResponse errorResponse = new ErrorResponse(GAME_ENDPOINT_NAME, null, WRONG_EPIDEMIC_CARDS);
            logger.warn("During the creation of the game, it was found that number of epidemic cards were {}", numPandemics);
            logger.warn(WRONG_EPIDEMIC_CARDS);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            uuid = gameService.createGame(numPandemics, numPlayers);
        } catch (GameExecutionException e) {
            ErrorResponse errorResponse = new ErrorResponse(GAME_ENDPOINT_NAME, null, WRONG_PLAYERS);
            logger.warn("During the creation of the game, it was found that number of players were {}", numPlayers);
            logger.warn(WRONG_PLAYERS);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        logger.info("Ending the creation of a game with {} players, {} pandemic cards and gameId {}", numPlayers, numPandemics, uuid.toString());

        SuccessResponse successResponse = new SuccessResponse(GAME_ENDPOINT_NAME, uuid, SUCCESS_GAME);
        return ResponseEntity.ok().body(successResponse);
    }

    @GetMapping("/game/read/{gameId}")
    ResponseEntity readGame(@PathVariable UUID gameId) {

        logger.info("Looking for game {}", gameId.toString());
        GameResponseDTO gameResponseDTO = gameService.getGameById(gameId);

        if(isNull(gameResponseDTO)) {
            ErrorResponse errorResponse = new ErrorResponse(GAME_ENDPOINT_NAME, gameId, GAME_NOT_FOUND);
            logger.warn("{}:{}", GAME_NOT_FOUND, gameId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        logger.info("Found game with gameId {} created at {} and updated at {}", gameId,
                gameResponseDTO.getInsertDateTime().format(formatter),
                gameResponseDTO.getUpdateDateTime().format(formatter));
        return ResponseEntity.ok().body(gameResponseDTO);
    }

}

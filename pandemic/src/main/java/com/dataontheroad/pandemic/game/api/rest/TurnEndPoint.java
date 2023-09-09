package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.turn.ExecutionSuccessResponse;
import com.dataontheroad.pandemic.game.api.model.turn.TurnRequestDTO;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/turn/execute")
    ResponseEntity executeTurn(@RequestBody TurnRequestDTO turnRequestDTO) {
        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(turnRequestDTO.getUuid());
        TurnInformation turnInformation;
        if(isNull(turnResponseDTO)) {
            return getErrorResponse(turnRequestDTO.getUuid(), GAME_NOT_FOUND);
        } else if (!turnResponseDTO.getActivePlayer().getName().equals(turnRequestDTO.getPlayerName())) {
            return getErrorResponse(turnRequestDTO.getUuid(), TURN_WRONG_PLAYER);
        } else if(turnRequestDTO.getActionPosition() >= turnResponseDTO.getActionList().size()) {
            return getErrorResponse(turnRequestDTO.getUuid(), TURN_WRONG_ACTION);
        }

        try {
            turnInformation = turnService.executeAction(turnRequestDTO.getUuid(), turnRequestDTO.getActionPosition());
        } catch (ActionException | GameExecutionException | EndOfGameException e) {
            return getErrorResponse(turnRequestDTO.getUuid(), e.getMessage());
        }

        ExecutionSuccessResponse successResponse =
                new ExecutionSuccessResponse(TURN_ENDPOINT_NAME,
                        turnRequestDTO.getUuid(), SUCCESS_ACTION,
                        turnInformation.getActivePlayer().getName(),
                        turnInformation.getActivePlayer().getCity().getName());
        return ResponseEntity.ok().body(successResponse);
    }

    private static ResponseEntity<ErrorResponse> getErrorResponse(UUID uuid, String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse(TURN_ENDPOINT_NAME, uuid, errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}

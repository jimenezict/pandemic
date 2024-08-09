package com.dataontheroad.pandemic.game.api.rest;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.commons.ErrorResponse;
import com.dataontheroad.pandemic.game.api.model.turn.*;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.*;
import static java.util.Objects.isNull;

@RestController
public class TurnEndPoint {

    private final TurnServiceImpl turnService;

    Logger logger = LoggerFactory.getLogger(TurnEndPoint.class);

    public TurnEndPoint(TurnServiceImpl turnService) {
        this.turnService = turnService;
    }

    @GetMapping("/turn/status/{gameId}")
    ResponseEntity getTurn(@PathVariable UUID gameId) {
        logger.info("Starting the status of turn request for gameId {}", gameId);
        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(gameId);
        if(isNull(turnResponseDTO)) {
            logger.warn("{} with Id {}", GAME_NOT_FOUND, gameId);
            ErrorResponse errorResponse = new ErrorResponse(TURN_ENDPOINT_NAME, gameId, GAME_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        logger.info("Ending the status of request for gameId {} for active player {} and available actions are {}", gameId,
                turnResponseDTO.getActivePlayer().getName(),
                turnResponseDTO.getActionList().size());

        return ResponseEntity.ok().body(turnResponseDTO);
    }

    @PostMapping("/turn/execute")
    ResponseEntity executeTurn(@RequestBody TurnRequestDTO turnRequestDTO) {
        logger.info("Starting the execute of turn request for gameId {}", turnRequestDTO.getUuid());
        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(turnRequestDTO.getUuid());

        if(isNull(turnResponseDTO)) {
            logger.warn("{}:{} during {}", GAME_NOT_FOUND, turnRequestDTO.getUuid(), TURN_ENDPOINT_NAME);
            return getErrorResponse(turnRequestDTO.getUuid(), GAME_NOT_FOUND);
        } else if (!turnResponseDTO.getActivePlayer().getName().equals(turnRequestDTO.getPlayerName())) {
            logger.warn("During {} it was requested an action for {} but {} was the active player during gameId {}",
                    TURN_ENDPOINT_NAME, turnRequestDTO.getPlayerName(),
                    turnResponseDTO.getActivePlayer(), turnRequestDTO.getUuid());
            return getErrorResponse(turnRequestDTO.getUuid(), TURN_WRONG_PLAYER);
        } else if(turnRequestDTO.getActionPosition() >= turnResponseDTO.getActionList().size()) {
            logger.warn("During {} it was requested the action {} but the highest allowed value was during gameId {}",
                    TURN_ENDPOINT_NAME, turnRequestDTO.getActionPosition(),
                    turnResponseDTO.getActionList().size() - 1, turnRequestDTO.getUuid());
            return getErrorResponse(turnRequestDTO.getUuid(), TURN_WRONG_ACTION);
        }

        TurnInformation turnInformation;
        TurnExecuteDTO turnExecuteDTO;

        try {
            turnExecuteDTO = turnService.getTurnExecuteDTO(turnRequestDTO.getUuid());
            Action action = turnService.getSelectedAction(turnExecuteDTO, turnRequestDTO.getActionPosition());
            logger.info("Player {} will do the action {} on the gameId {}",
                    turnExecuteDTO.getActivePlayer().getName(), action.actionPrompt(), turnRequestDTO.getUuid());
            turnService.validateActionFormat(turnExecuteDTO, action, (HashMap<String, String>) turnRequestDTO.getAdditionalFields());
            turnInformation = turnService.executeAction(turnRequestDTO.getUuid(), action);
            logger.info("Player {} will do the action {} on the gameId {}",
                    turnExecuteDTO.getActivePlayer().getName(), action.actionPrompt(), turnRequestDTO.getUuid());
            turnService.ifEndOfGameThrowExcepction(turnRequestDTO.getUuid());
        } catch(EndOfGameException e) {
            EndOfGameResponse endOfGameResponse = new EndOfGameResponse(TURN_ENDPOINT_NAME, turnRequestDTO.getUuid(), e.getReasonOfEndGame());
            endOfGameResponse.setWin(e.getDidPlayerWon());
            logger.info("GameId {} has ended with reason {}", endOfGameResponse.getGameID(), endOfGameResponse.getMessage());
            return ResponseEntity.ok().body(endOfGameResponse);
        } catch (ActionException | GameExecutionException e) {
            return getErrorResponse(turnRequestDTO.getUuid(), e.getMessage());
        }

        logger.info("Ending the execute of turn request for gameId {}, next user is {}", turnRequestDTO.getUuid(), turnInformation.getActivePlayer());
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

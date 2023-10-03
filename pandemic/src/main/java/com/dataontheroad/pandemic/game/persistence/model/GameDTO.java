package com.dataontheroad.pandemic.game.persistence.model;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.model.board.Board;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.dataontheroad.pandemic.model.board.BoardFactory.createBoardWithNumberOfPlayers;
import static java.util.UUID.randomUUID;

public class GameDTO {

    private final UUID uuid;

    private final LocalDateTime insertDateTime;

    private final Board board;

    private LocalDateTime updateDateTime;
    
    private final TurnInformation turnInformation;

    public GameDTO(int numOfPlayers) throws GameExecutionException {
        this.uuid = randomUUID();
        insertDateTime = LocalDateTime.now();
        board = createBoardWithNumberOfPlayers(numOfPlayers);
        turnInformation = new TurnInformation(board.getPlayers().get(0));
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getLocalDate() {
        return insertDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Board getBoard() {
        return board;
    }

    public TurnInformation getTurnInformation() {
        return turnInformation;
    }
}

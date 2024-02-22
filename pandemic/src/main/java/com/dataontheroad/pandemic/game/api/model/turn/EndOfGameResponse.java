package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.game.api.model.commons.SuccessResponse;

import java.util.UUID;

public class EndOfGameResponse extends SuccessResponse {

    private boolean win;

    public EndOfGameResponse(String endpoint, UUID gameID, String message) {
        super(endpoint, gameID, message);
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}

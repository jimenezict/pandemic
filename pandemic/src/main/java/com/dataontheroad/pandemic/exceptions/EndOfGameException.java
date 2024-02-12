package com.dataontheroad.pandemic.exceptions;

public class EndOfGameException extends Exception {

    private final String reasonOfEndGame;
    public EndOfGameException(String reasonOfEndGame) {
        super("Game should finish because: " + reasonOfEndGame);
        this.reasonOfEndGame = reasonOfEndGame;
    }

    public String getReasonOfEndGame() {
        return reasonOfEndGame;
    }


}

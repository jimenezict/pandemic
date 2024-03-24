package com.dataontheroad.pandemic.exceptions;

public class EndOfGameException extends Exception {

    private final boolean didPlayerWon;
    private final String reasonOfEndGame;
    public EndOfGameException(String reasonOfEndGame) {
        super("Game should finish because: " + reasonOfEndGame);
        this.reasonOfEndGame = reasonOfEndGame;
        didPlayerWon = false;
    }

    public EndOfGameException(String reasonOfEndGame, boolean didPlayerWon) {
        super("Game should finish because: " + reasonOfEndGame);
        this.reasonOfEndGame = reasonOfEndGame;
        this.didPlayerWon = didPlayerWon;
    }

    public String getReasonOfEndGame() {
        return reasonOfEndGame;
    }

    public boolean getDidPlayerWon() {
        return didPlayerWon;
    }


}

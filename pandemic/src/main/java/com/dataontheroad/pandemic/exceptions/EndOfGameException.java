package com.dataontheroad.pandemic.exceptions;

public class EndOfGameException extends Exception {

    private boolean win;
    private final String reasonOfEndGame;
    public EndOfGameException(String reasonOfEndGame) {
        super("Game should finish because: " + reasonOfEndGame);
        this.reasonOfEndGame = reasonOfEndGame;
        win = false;
    }

    public EndOfGameException(String reasonOfEndGame, boolean win) {
        super("Game should finish because: " + reasonOfEndGame);
        this.reasonOfEndGame = reasonOfEndGame;
        this.win = win;
    }

    public String getReasonOfEndGame() {
        return reasonOfEndGame;
    }

    public boolean getWin() {
        return win;
    }


}

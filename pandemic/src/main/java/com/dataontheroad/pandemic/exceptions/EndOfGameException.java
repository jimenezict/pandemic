package com.dataontheroad.pandemic.exceptions;

public class EndOfGameException extends Exception {
    public EndOfGameException(String reasonOfEndGame) {
        super("Game should finish because: " + reasonOfEndGame);
    }
}

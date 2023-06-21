package com.dataontheroad.pandemic.exceptions;

public class GameExecutionException extends Throwable {
    public GameExecutionException(String message) {
        super("An error happens during the execution: " + message);
    }
}

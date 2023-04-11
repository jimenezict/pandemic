package com.dataontheroad.pandemic.exceptions;

import com.dataontheroad.pandemic.actions.ActionsType;

public class EndOfGameException extends Exception {
    public EndOfGameException(String reasonOfEndGame) {
        super("Game should finish because: " + reasonOfEndGame);
    }
}

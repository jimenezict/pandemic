package com.dataontheroad.pandemic.exceptions;


import com.dataontheroad.pandemic.actions.ActionsType;

public class ActionException extends Exception {
    public ActionException(ActionsType action, String errorMessage) {
        super("Action " + action.label + " got Exception with message: " + errorMessage);
    }
}
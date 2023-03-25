package com.dataontheroad.pandemic.board.model.enums;

public enum CardType {
    INFECTION, // Card on the board deck, decides where an infection box is placed
    CITY, // Card of cities on the players deck
    SPECIAL_ACTION, // Card on the players deck do not represent a city
    EPIDEMIC // Card on the players deck which expands a virus

}

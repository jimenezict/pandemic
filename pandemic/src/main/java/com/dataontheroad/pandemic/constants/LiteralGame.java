package com.dataontheroad.pandemic.constants;

import static com.dataontheroad.pandemic.actions.ActionsType.FLYCHARTER;

public class LiteralGame {

    private LiteralGame() {
        throw new IllegalStateException("Constant Class");
    }
    public static final String TURN_ENDPOINT_NAME = "Turn End Point";
    public static final String GAME_ENDPOINT_NAME = "Game End Point";
    public static final String GAME_NOT_FOUND = "Game not found";
    public static final String WRONG_EPIDEMIC_CARDS = "Error creating game, epidemic cards should be between 0 and 6";
    public static final String WRONG_PLAYERS = "Error creating game, players should be between 2 and 4";
    public static final String SUCCESS_GAME = "Game created";
    public static final String SUCCESS_ACTION = "Action executed correctly";
    public static final String TURN_WRONG_PLAYER = "Error executing action, requester is not the active player";
    public static final String TURN_WRONG_ACTION = "Error executing action, requester is not executing correct action";
    public static final String TURN_WRONG_FLYCHARTER_INVALID_DESTINATION_CITY = "destination is not a valid city on action " + FLYCHARTER.name();
    public static final String TURN_WRONG_FLYCHARTER_DESTINATION_FIELD = "destination field is mandatory when action is " + FLYCHARTER.name();
    public static final String END_OF_GAME_EMPTY_DECK = "Player Deck is empty";
    public static final String ADDITIONAL_FIELD_DESTINATION = "destination";

}

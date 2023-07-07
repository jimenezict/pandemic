package com.dataontheroad.pandemic.constants;

public class LiteralGame {

    private LiteralGame() {
        throw new IllegalStateException("Constant Class");
    }
    public static final String TURN_ENDPOINT_NAME = "Turn End Point";
    public static final String GAME_ENDPOINT_NAME = "Game End Point";
    public static final String GAME_NOT_FOUND = "Game not found";
    public static final String WRONG_PLAYERS = "Error creating game, epidemic cards should be between 0 and 6";
    public static final String WRONG_EPIDEMIC_CARDS = "Error creating game, players should be between 2 and 4";
    public static final String SUCCESS_GAME = "Game created";
    public static final String SUCCESS_ACTION = "Action executed correctly";
    public static final String TURN_WRONG_PLAYER = "Error executing action, requester is not the active player";
    public static final String TURN_WRONG_ACTION = "Error executing action, requester is not executing correct action";

}

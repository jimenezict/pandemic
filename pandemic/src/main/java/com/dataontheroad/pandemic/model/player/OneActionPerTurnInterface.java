package com.dataontheroad.pandemic.model.player;

public interface OneActionPerTurnInterface {

    boolean canPlayerExecuteActionThisTurn();

    void resetActionAvailable();

    void actionHasBeenExecuted();
}

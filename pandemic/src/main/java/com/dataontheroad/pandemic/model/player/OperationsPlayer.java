package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.player_services.OperationsBuildResearchCenterService;
import com.dataontheroad.pandemic.actions.player_services.OperationsFlyFromReserachService;
import com.dataontheroad.pandemic.actions.player_services.SpecialActionInterface;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class OperationsPlayer extends Player implements OneActionPerTurnInterface, SpecialActionInterface {

    private boolean isActionAvailableThisTurn;
    private transient OperationsFlyFromReserachService operationsFlyFromReserachService;

    public OperationsPlayer() {
        super();
        color = OPERATIONS_COLOR;
        name = OPERATIONS_NAME;
        description = OPERATIONS_DESCRIPTION;
        isActionAvailableThisTurn = true;
        buildResearchCenterDefaultService = new OperationsBuildResearchCenterService();
        operationsFlyFromReserachService = new OperationsFlyFromReserachService();
    }
    @Override
    public boolean canPlayerExecuteActionThisTurn() {
        return isActionAvailableThisTurn;
    }

    @Override
    public void resetActionAvailable() {
        isActionAvailableThisTurn = true;
    }

    @Override
    public void actionHasBeenExecuted() {
        isActionAvailableThisTurn = false;
    }

    public OperationsFlyFromReserachService specialActionService() {
        return operationsFlyFromReserachService;
    }
}

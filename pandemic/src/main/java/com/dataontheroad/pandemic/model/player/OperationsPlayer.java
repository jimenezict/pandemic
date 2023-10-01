package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.player_services.OperationsBuildResearchCenterService;
import com.dataontheroad.pandemic.actions.player_services.OperationsFlyFromReserachService;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class OperationsPlayer extends Player implements OneActionPerTurnInterface {

    private boolean isActionAvailableThisTurn;

    public OperationsPlayer() {
        super();
        color = OPERATIONS_COLOR;
        name = OPERATIONS_NAME;
        description = OPERATIONS_DESCRIPTION;
        isActionAvailableThisTurn = true;
        buildResearchCenterDefaultService = new OperationsBuildResearchCenterService();
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
        return OperationsFlyFromReserachService.getInstance();
    }
}

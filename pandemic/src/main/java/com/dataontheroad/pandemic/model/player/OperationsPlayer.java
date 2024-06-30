package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.BuildResearchCenterDefaultService;
import com.dataontheroad.pandemic.actions.player_services.OperationsBuildResearchCenterService;
import com.dataontheroad.pandemic.actions.player_services.OperationsFlyFromReserachService;
import com.dataontheroad.pandemic.actions.player_services.SpecialActionInterface;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class OperationsPlayer extends Player implements OneActionPerTurnInterface, SpecialActionInterface {

    private boolean isActionAvailableThisTurn;

    public OperationsPlayer() {
        super();
        color = OPERATIONS_COLOR;
        name = OPERATIONS_NAME;
        description = OPERATIONS_DESCRIPTION;
        isActionAvailableThisTurn = true;
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

    @Override
    public BuildResearchCenterDefaultService getBuildResearchCenter() {
        return OperationsBuildResearchCenterService.getInstance();
    }

    public OperationsFlyFromReserachService specialActionService() {
        return OperationsFlyFromReserachService.getInstance();
    }
}

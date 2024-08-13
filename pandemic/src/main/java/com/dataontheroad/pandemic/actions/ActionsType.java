package com.dataontheroad.pandemic.actions;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;

public enum ActionsType {
    BUILDRESEARCHSTATION(BUILDRESEARCHSTATION_DESCRIPTION),
    SHAREKNOWLEDGE(SHAREKNOWLEDGE_DESCRIPTION),
    SHUTTLEFLIGHT(SHUTTLEFLIGHT_DESCRIPTION),
    DRIVEFERRY(DRIVEFERRY_DESCRIPTION),
    FLYDIRECT(FLYDIRECT_DESCRIPTION),
    FLYCHARTER(FLYCHARTER_DESCRIPTION),
    DISCOVERCURE(DISCOVERCURE_DESCRIPTION),
    TREATDISEASE(TREATDISEASE_DESCRIPTION),
    PLAYERACTION(PLAYERACTION_DESCRIPTION),
    TAKEDISCARDEVENTCARD(TAKEDISCARDEVENTCARD_DESCRIPTION),
    DRIVEFERRYDISPATCHER(DRIVEFERRYDISPATCHER_DESCRIPTION),
    MOVEPAWNTOPAWN(MOVEPAWNTOPAWN_DESCRIPTION),
    OPERATION_FLY(OPERATIONFLY_DESCRIPTION);

    public final String label;
    ActionsType(String label) {
        this.label = label;
    }
}

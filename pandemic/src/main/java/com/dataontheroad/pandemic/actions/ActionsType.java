package com.dataontheroad.pandemic.actions;

import static com.dataontheroad.pandemic.constants.Literals.*;

public enum ActionsType {
    BUILDRESEARCHSTATION(BUILDRESEARCHSTATION_DESCRIPTION),
    SHAREKNOWLEDGE(SHAREKNOWLEDGE_DESCRIPTION),
    SHUTTLEFLIGHT(SHUTTLEFLIGHT_DESCRIPTION),
    DRIVEFERRY(DRIVEFERRY_DESCRIPTION),
    FLYDIRECT(FLYDIRECT_DESCRIPTION),
    FLYCHARTER(FLYCHARTER_DESCRIPTION),
    DISCOVERCURE(DISCOVERCURE_DESCRIPTION);

    public final String label;
    private ActionsType(String label) {
        this.label = label;
    }
}

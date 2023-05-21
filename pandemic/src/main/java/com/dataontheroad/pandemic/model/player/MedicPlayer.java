package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.TreatDiseaseDefaultService;
import com.dataontheroad.pandemic.actions.player_services.TreatDiseaseMedicService;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class MedicPlayer extends Player {

    public MedicPlayer() {
        super();
        color = MEDIC_COLOR;
        name = MEDIC_NAME;
        description = MEDIC_DESCRIPTION;
    }

    @Override
    public TreatDiseaseDefaultService getTreatDisease() { return TreatDiseaseMedicService.getInstance();}

}


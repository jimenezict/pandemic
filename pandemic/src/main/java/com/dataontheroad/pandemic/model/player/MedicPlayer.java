package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.player_services.TreatDiseaseMedicService;
import com.dataontheroad.pandemic.model.virus.Virus;

import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class MedicPlayer extends Player {

    public MedicPlayer() {
        super();
        color = MEDIC_COLOR;
        name = MEDIC_NAME;
        description = MEDIC_DESCRIPTION;
        treatDiseaseDefaultService = new TreatDiseaseMedicService();
    }

    public void executeAfterAction(List<Virus> virusList) {
        virusList.stream()
                .filter(Virus::getCureDiscovered)
                .forEach(virus -> getCity().getVirusBoxes().removeIf(boxVirus -> virus.getVirusType().equals(boxVirus)));
    }

}


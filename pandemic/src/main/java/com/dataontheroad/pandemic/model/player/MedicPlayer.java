package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.TreatDiseaseDefaultService;
import com.dataontheroad.pandemic.actions.player_services.TreatDiseaseMedicService;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class MedicPlayer extends Player implements PreventPropagationOfVirusInterface {

    public MedicPlayer() {
        super();
        color = MEDIC_COLOR;
        name = MEDIC_NAME;
        description = MEDIC_DESCRIPTION;
    }

    public void executeAfterAction(List<Virus> virusList) {
        virusList.stream()
                .filter(Virus::getCureDiscovered)
                .forEach(virus -> getCity().getVirusBoxes().removeIf(boxVirus -> virus.getVirusType().equals(boxVirus)));
    }

    @Override
    public List<City> getCitiesWherePreventsToPropagate(List<VirusType> listOfVirus) {
        return Arrays.asList(this.getCity());
    }

    @Override
    public TreatDiseaseDefaultService getTreatDisease() {
        return TreatDiseaseMedicService.getInstance();
    }

}


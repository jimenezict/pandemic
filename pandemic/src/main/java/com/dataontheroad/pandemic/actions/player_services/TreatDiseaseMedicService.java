package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.default_services.TreatDiseaseDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.MedicPlayer;
import com.dataontheroad.pandemic.model.virus.Virus;

import static com.dataontheroad.pandemic.constants.LiteralsAction.TREATDISEASE_ERROR_DO_NOT_EXISTS_VIRUS;

public class TreatDiseaseMedicService extends TreatDiseaseDefaultService{

    public void doAction(MedicPlayer player, Virus virus) throws ActionException {
        City position = player.getCity();
        if(!position.getVirusBoxes().contains(virus.getVirusType())) {
            throw new ActionException(ActionsType.TREATDISEASE, TREATDISEASE_ERROR_DO_NOT_EXISTS_VIRUS);
        }
        position.getVirusBoxes().removeIf(virusBox -> virusBox.name().equals(virus.getVirusType().name()));
    }
}

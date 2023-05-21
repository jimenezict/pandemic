package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.TreatDiseaseAction;
import com.dataontheroad.pandemic.actions.default_services.TreatDiseaseDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.MedicPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.TREATDISEASE_ERROR_DO_NOT_EXISTS_VIRUS;
import static java.util.Objects.isNull;

public class TreatDiseaseMedicService extends TreatDiseaseDefaultService{

    private static TreatDiseaseMedicService treatDiseaseMedicService;

    private TreatDiseaseMedicService() {
        super();
    }

    public static TreatDiseaseMedicService getInstance() {
        if(isNull(treatDiseaseMedicService)) {
            treatDiseaseMedicService = new TreatDiseaseMedicService();
        }
        return treatDiseaseMedicService;
    }

    public static void doAction(MedicPlayer player, Virus virus) throws ActionException {
        City position = player.getCity();
        if(!position.getVirusBoxes().contains(virus.getVirusType())) {
            throw new ActionException(ActionsType.TREATDISEASE, TREATDISEASE_ERROR_DO_NOT_EXISTS_VIRUS);
        }
        position.getVirusBoxes().removeIf(virusBox -> virusBox.name().equals(virus.getVirusType().name()));
    }
}

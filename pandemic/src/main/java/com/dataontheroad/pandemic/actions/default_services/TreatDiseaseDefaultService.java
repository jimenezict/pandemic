package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.TreatDiseaseAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.TREATDISEASE_ERROR_DO_NOT_EXISTS_VIRUS;
import static java.util.Objects.isNull;

public class TreatDiseaseDefaultService {

    private static TreatDiseaseDefaultService treatDiseaseDefaultService;

    protected TreatDiseaseDefaultService() {

    }

    public static TreatDiseaseDefaultService getInstance() {
        if(isNull(treatDiseaseDefaultService)) {
            treatDiseaseDefaultService = new TreatDiseaseDefaultService();
        }
        return treatDiseaseDefaultService;
    }

    public boolean isDoable(Player player) {
        return !player.getCity().getVirusBoxes().isEmpty();
    }

    public List<Action> returnAvailableActions(Player player, List<Virus> virusList) {
        City position = player.getCity();
        return position.getVirusBoxes().stream().distinct().map(virus -> {
            Virus virusToReturn = virusList.get(virusList.indexOf(new Virus(virus)));
            return new TreatDiseaseAction(player, virusToReturn);
        }).collect(Collectors.toList());
    }

    public void doAction(Player player, Virus virus) throws ActionException {
        City position = player.getCity();
        if(!position.getVirusBoxes().contains(virus.getVirusType())) {
            throw new ActionException(ActionsType.TREATDISEASE, TREATDISEASE_ERROR_DO_NOT_EXISTS_VIRUS);
        }
        if(virus.getCureDiscovered()) {
            //When cure is discovered can remove all the boxes of the same color with a unique action
            position.getVirusBoxes().removeIf(virusBox -> virusBox.name().equals(virus.getVirusType().name()));
        } else {
            position.getVirusBoxes().remove(virus.getVirusType());
        }
    }
}

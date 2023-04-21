package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.SHAREKNOWLEDGE_ACTION;
import static com.dataontheroad.pandemic.constants.LiteralsAction.TREATDISEASE_ACTION;

public class TreatDiseaseAction extends Action {

    private Virus virus;

    public TreatDiseaseAction(Player player, Virus virus) {
        super(ActionsType.TREATDISEASE, player);
        this.virus = virus;
    }


    @Override
    public String actionPrompt() {
        return TREATDISEASE_ACTION + virus.getVirusType().toString();
    }

    @Override
    public void execute() throws ActionException {
        this.player.getTreatDisease().doAction(player, virus);
    }
}

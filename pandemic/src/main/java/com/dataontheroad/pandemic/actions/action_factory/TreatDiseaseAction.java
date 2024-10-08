package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import static com.dataontheroad.pandemic.constants.LiteralsAction.TREATDISEASE_ACTION;

public class TreatDiseaseAction extends Action {

    private final Virus virus;

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

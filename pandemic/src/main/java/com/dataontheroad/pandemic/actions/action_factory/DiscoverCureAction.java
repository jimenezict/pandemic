package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DISCOVERCURE_ACTION;

public class DiscoverCureAction extends Action {

    private final Virus virus;

    public DiscoverCureAction(Player player, Virus virus) {
        super(ActionsType.DISCOVERCURE, player);
        this.virus = virus;
    }

    @Override
    public String actionPrompt() {
        return DISCOVERCURE_ACTION + virus.getVirusType().name();
    }

    @Override
    public void execute() throws ActionException {
        this.player.getDiscoverCure().doAction(player, virus);
    }
}

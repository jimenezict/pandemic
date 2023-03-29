package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.board.model.Virus;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

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
        getPlayer().getDiscoverCure().doAction(player, virus);
    }
}

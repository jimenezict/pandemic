package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.Virus;
import com.dataontheroad.pandemic.model.VirusType;

import static com.dataontheroad.pandemic.constants.Literals.DISCOVERCURE_ACTION;

public class DiscoverCureAction extends Action {

    private final VirusType virusType;

    public DiscoverCureAction(Player player, Virus virus) {
        super(ActionsType.DISCOVERCURE, player);
        virusType = virus.getVirusType();
    }

    @Override
    public String actionPrompt() {
        return DISCOVERCURE_ACTION + virusType.name();
    }
}

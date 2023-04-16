package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ACTION;

public class BuildResearchCenterAction extends Action {

    public BuildResearchCenterAction(Player player) {
        super(ActionsType.BUILDRESEARCHSTATION, player);
    }

    @Override
    public String actionPrompt() {
        return BUILDRESEARCHSTATION_ACTION + player.getCity().getName();
    }

    @Override
    public void execute() throws ActionException {
        this.player.getBuildResearchCenter().doAction(player);
    }

}

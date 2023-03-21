package com.dataontheroad.pandemic.actions.model;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.services.BuildResearchCenter;
import com.dataontheroad.pandemic.model.Player;

public class BuildResearchCenterAction extends Action {

    public BuildResearchCenterAction(Player player) {
        super(ActionsType.BUILDRESEARCHCENTER, player);
    }

    @Override
    public String actionPrompt() {
        return "Build Research Center at city: " + player.getCity().getName();
    }
}

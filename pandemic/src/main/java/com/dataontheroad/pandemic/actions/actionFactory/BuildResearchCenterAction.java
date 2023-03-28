package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.defaultServices.BuildResearchCenter;
import com.dataontheroad.pandemic.board.model.Player;
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
        BuildResearchCenter.doAction(player);
    }

}

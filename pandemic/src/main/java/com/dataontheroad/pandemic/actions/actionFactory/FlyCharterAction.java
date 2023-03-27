package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.board.model.Player;

import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYCHARTER_ACTION;

public class FlyCharterAction extends Action {

    public FlyCharterAction(Player player) {
        super(ActionsType.FLYCHARTER, player);
    }

    @Override
    public String actionPrompt() {
        return FLYCHARTER_ACTION;
    }
}

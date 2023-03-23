package com.dataontheroad.pandemic.actions.model;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.FLYCHARTER_ACTION;

public class FlyCharterAction extends Action {

    public FlyCharterAction(Player player) {
        super(ActionsType.FLYCHARTER, player);
    }

    @Override
    public String actionPrompt() {
        return FLYCHARTER_ACTION;
    }
}

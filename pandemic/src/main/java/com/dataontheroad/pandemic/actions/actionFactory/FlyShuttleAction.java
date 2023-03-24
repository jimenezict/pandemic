package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.SHUTTLEFLIGHT_ACTION;

public class FlyShuttleAction extends Action {

    City destination;

    public FlyShuttleAction(Player player, City destination) {
        super(ActionsType.SHUTTLEFLIGHT, player);
        this.destination = destination;
    }

    @Override
    public String actionPrompt() {
        return SHUTTLEFLIGHT_ACTION + destination.getName();
    }
}

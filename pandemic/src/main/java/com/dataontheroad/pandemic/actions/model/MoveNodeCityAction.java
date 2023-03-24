package com.dataontheroad.pandemic.actions.model;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.model.City;
import com.dataontheroad.pandemic.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.DRIVEFERRY_ACTION;
import static com.dataontheroad.pandemic.constants.Literals.SHUTTLEFLIGHT_ACTION;

public class MoveNodeCityAction extends Action {

    City destination;

    public MoveNodeCityAction(Player player, City destination) {
        super(ActionsType.DRIVEFERRY, player);
        this.destination = destination;
    }

    @Override
    public String actionPrompt() {
        return DRIVEFERRY_ACTION + destination.getName();
    }
}

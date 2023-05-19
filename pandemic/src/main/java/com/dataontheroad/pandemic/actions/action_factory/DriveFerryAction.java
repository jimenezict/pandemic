package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ACTION;

public class DriveFerryAction extends Action {

    City destination;

    public DriveFerryAction(Player player, City destination) {
        super(ActionsType.DRIVEFERRY, player);
        this.destination = destination;
    }

    @Override
    public String actionPrompt() {
        return DRIVEFERRY_ACTION + destination.getName();
    }

    @Override
    public void execute() throws ActionException {
        this.player.getDriveFerry().doAction(player, destination);
    }
}

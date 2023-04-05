package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

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

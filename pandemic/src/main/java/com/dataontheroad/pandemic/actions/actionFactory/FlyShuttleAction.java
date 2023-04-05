package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.exceptions.ActionException;

import static com.dataontheroad.pandemic.constants.LiteralsAction.SHUTTLEFLIGHT_ACTION;

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

    @Override
    public void execute() throws ActionException {
        this.player.getFlyShuttle().doAction(player, destination);
    }
}

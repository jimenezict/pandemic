package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYDIRECT_ACTION;

public class FlyDirectAction extends Action {

    final City destination;

    public FlyDirectAction(Player player, City destination) {
        super(ActionsType.FLYDIRECT, player);
        this.destination = destination;
    }

    @Override
    public String actionPrompt() {
        return FLYDIRECT_ACTION + destination.getName();
    }

    @Override
    public void execute() throws ActionException {
        this.player.getFlyDirectCity().doAction(player, destination);
    }
}

package com.dataontheroad.pandemic.actions.action_factory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYCHARTER_ACTION;

public class FlyCharterAction extends Action implements ICommuting {
    City destination;

    public FlyCharterAction(Player player) {
        super(ActionsType.FLYCHARTER, player);
    }

    @Override
    public String actionPrompt() {
        return FLYCHARTER_ACTION;
    }

    @Override
    public void execute() throws ActionException {
        this.player.getFlyCharter().doAction(player, destination);
    }

    @Override
    public void setDestination(City destination) {
        this.destination = destination;
    }

    @Override
    public City getDestination() {
        return destination;
    }
}

package com.dataontheroad.pandemic.actions.actionFactory;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.Player;

import static com.dataontheroad.pandemic.constants.Literals.FLYDIRECT_ACTION;

public class FlyDirectAction extends Action {

    City destination;

    public FlyDirectAction(Player player, City destination) {
        super(ActionsType.FLYDIRECT, player);
        this.destination = destination;
    }

    @Override
    public String actionPrompt() {
        return FLYDIRECT_ACTION + destination.getName();
    }
}

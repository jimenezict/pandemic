package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.player_services.DispatcherMovePawnToPawnService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.MOVEPAWNTOPAWN_ACTION;

public class MovePawnToPawnAction extends Action {
    City destination;
    List<Player> playerList;

    public MovePawnToPawnAction(List<Player> playerList, Player player, City destination) {
        super(ActionsType.MOVEPAWNTOPAWN, player);
        this.destination = destination;
        this.playerList = playerList;
    }

    @Override
    public String actionPrompt() {
        return player.getName() + MOVEPAWNTOPAWN_ACTION + destination.getName();
    }

    @Override
    public void execute() throws ActionException {
        DispatcherMovePawnToPawnService.doAction(playerList, player, destination);
    }
}

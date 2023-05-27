package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.player_services.DispatcherAllPlayerMovementsService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import static com.dataontheroad.pandemic.constants.LiteralsAction.*;

public class AllPlayersMovementsAction extends Action {
    City destination;

    public AllPlayersMovementsAction(Player player, City destination) {
        super(ActionsType.DRIVEFERRYDISPATCHER, player);
        this.destination = destination;
    }

    @Override
    public String actionPrompt() {
        return player.getName() + DRIVEFERRYDISPATCHER_ACTION + destination.getName();
    }

    @Override
    public void execute() throws ActionException {
        DispatcherAllPlayerMovementsService.doAction(player, destination);
    }

}

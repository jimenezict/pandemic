package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.AllPlayersMovementsAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.DRIVEFERRY_ERROR_NO_CONNECTION;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.DISPATCHER_NAME;
import static java.util.Objects.isNull;

public class DispatcherAllPlayerMovementsService {

    private static DispatcherAllPlayerMovementsService dispatcherAllPlayerMovementsService;

    private DispatcherAllPlayerMovementsService() {
    }

    public static DispatcherAllPlayerMovementsService getInstance() {
        if(isNull(dispatcherAllPlayerMovementsService)) {
            dispatcherAllPlayerMovementsService = new DispatcherAllPlayerMovementsService();
        }
        return dispatcherAllPlayerMovementsService;
    }

    public static boolean isDoable(Player player, City destination) {
        return player.getCity().getNodeCityConnection().contains(destination);
    }

    public static List<Action> returnAvailableActions(List<Player> listOfPlayer) {
        List<Action> actionsToReturn = new ArrayList<>();

        listOfPlayer.stream()
                .filter(iteratePlayer -> !DISPATCHER_NAME.equals(iteratePlayer.getName()))
                .forEach(iteratePlayer ->
                    actionsToReturn.addAll(iteratePlayer.getCity().getNodeCityConnection().stream()
                            .filter(destination -> !iteratePlayer.getCity().equals(destination))
                            .map(destination -> new AllPlayersMovementsAction(iteratePlayer, destination))
                            .collect(Collectors.toList()))
                );

        return actionsToReturn;
    }

    public static void doAction(Player player, City destination) throws ActionException {
        if(!player.getCity().getNodeCityConnection().contains(destination)) {
            throw new ActionException(ActionsType.DRIVEFERRYDISPATCHER, DRIVEFERRY_ERROR_NO_CONNECTION);
        }

        player.setCity(destination);
    }
}

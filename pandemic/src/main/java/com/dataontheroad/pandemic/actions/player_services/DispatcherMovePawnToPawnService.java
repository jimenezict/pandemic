package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.MovePawnToPawnAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.MOVEPAWNTOPAWN_ERROR_DESTINATION_NOT_AVAILABLE;
import static java.util.Objects.isNull;

public class DispatcherMovePawnToPawnService {
    private static DispatcherMovePawnToPawnService dispatcherMovePawnToPawnService;

    private DispatcherMovePawnToPawnService() {
    }

    public static DispatcherMovePawnToPawnService getInstance() {
        if(isNull(dispatcherMovePawnToPawnService)) {
            dispatcherMovePawnToPawnService = new DispatcherMovePawnToPawnService();
        }
        return dispatcherMovePawnToPawnService;
    }

    public boolean isDoable(List<Player> players, Player player, City destination) {
        return playerIsNotAlreadyOnDestination(player, destination) &&
                thereIsPawnOnDestination(players, destination);
    }

    public List<Action> returnAvailableActions(List<Player> players) {
        List<Action> availableMovePawnToPawnAction = new ArrayList<>();
        players.stream().forEach(playerOrigin ->
            players.stream()
                    .filter(playerDestination -> isDoable(players, playerOrigin, playerDestination.getCity()))
                    .forEach(playerDestination -> availableMovePawnToPawnAction.add(new MovePawnToPawnAction(players, playerOrigin, playerDestination.getCity()))

        ));
        return availableMovePawnToPawnAction;
    }

    public void doAction(List<Player> players, Player player, City destination) throws ActionException {
        if(!isDoable(players, player, destination)) {
            throw new ActionException(ActionsType.MOVEPAWNTOPAWN, MOVEPAWNTOPAWN_ERROR_DESTINATION_NOT_AVAILABLE);
        }
        player.setCity(destination);
    }

    private boolean thereIsPawnOnDestination(List<Player> players, City destination) {
        return players.stream().filter(player1 -> player1.getCity().equals(destination)).count() > 0;
    }

    private boolean playerIsNotAlreadyOnDestination(Player player, City destination) {
        return !player.getCity().equals(destination);
    }
}

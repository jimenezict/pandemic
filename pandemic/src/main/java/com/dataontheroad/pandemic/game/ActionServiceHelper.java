package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.player_services.DispatcherAllPlayerMovementsService;
import com.dataontheroad.pandemic.actions.player_services.DispatcherMovePawnToPawnService;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class ActionServiceHelper {

    private ActionServiceHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Action> getListOfActions(Player player, List<Virus> virusList, List<City> citiesWithResearchCenter, List<Player> otherPlayersOnTheCity, List<City> boardCities) {
        List<Action> allowedActions = new ArrayList<>();
        allowedActions.addAll(player.getBuildResearchCenter().returnAvailableActions(player));
        allowedActions.addAll(player.getDiscoverCure().returnAvailableActions(player, virusList));
        allowedActions.addAll(player.getFlyCharter().returnAvailableActions(player));
        allowedActions.addAll(player.getFlyDirectCity().returnAvailableActions(player, boardCities));
        allowedActions.addAll(player.getFlyShuttle().returnAvailableActions(player, citiesWithResearchCenter));
        allowedActions.addAll(player.getDriveFerry().returnAvailableActions(player));
        allowedActions.addAll(player.getShareKnowledge().returnAvailableActions(player, otherPlayersOnTheCity));
        allowedActions.addAll(player.getTreatDisease().returnAvailableActions(player, virusList));
        return allowedActions;
    }

    public static List<Action> getListOfSpecialActions(Player player, List<Player> listOfPlayers, List<BaseCard> discardedCards) {

        switch (Optional.ofNullable(player.getName()).orElse("")) {
            case OPERATIONS_NAME:
                OperationsPlayer operationsPlayer = (OperationsPlayer) player;
                return operationsPlayer.specialActionService().returnAvailableActions(operationsPlayer);
            case CONTINGENCY_NAME:
                ContingencyPlayer contingencyPlayer = (ContingencyPlayer) player;
                return contingencyPlayer.specialActionService().returnAvailableActions(contingencyPlayer, discardedCards);
            case DISPATCHER_NAME:
                List<Action> allowedActions = new ArrayList<>();
                allowedActions.addAll(DispatcherMovePawnToPawnService.getInstance().returnAvailableActions(listOfPlayers));
                allowedActions.addAll(DispatcherAllPlayerMovementsService.getInstance().returnAvailableActions(listOfPlayers));
                return allowedActions;
            default:
                return new ArrayList<>();
        }
    }
}

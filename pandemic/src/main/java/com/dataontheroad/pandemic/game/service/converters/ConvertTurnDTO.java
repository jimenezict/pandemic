package com.dataontheroad.pandemic.game.service.converters;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseCity;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponsePlayer;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertTurnDTO {

    private ConvertTurnDTO() {
        throw new IllegalStateException("Utility class");
    }

    public static TurnResponsePlayer convertTurnResponsePlayer(Player playerTurnInformation) {
        List<String> cardsList = playerTurnInformation.getListCard().stream().map(Object::toString).collect(Collectors.toList());

        return new TurnResponsePlayer(cardsList, new TurnResponseCity(playerTurnInformation.getCity()), playerTurnInformation.getName());
    }

    public static List<String> convertListActionsToString(List<Action> actionList) {
        return actionList.stream().map(action -> action.getActionsType() + ":" + action.actionPrompt()).collect(Collectors.toList());
    }
}

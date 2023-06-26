package com.dataontheroad.pandemic.game.service.converters;

import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseCity;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponsePlayer;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertTurnDTO {

    public static TurnResponsePlayer convertTurnResponsePlayer(Player playerTurnInformation) {
        List<String> cardsList = playerTurnInformation.getListCard().stream().map(card -> card.toString()).collect(Collectors.toList());

        TurnResponsePlayer turnResponsePlayer =
                new TurnResponsePlayer(cardsList, new TurnResponseCity(playerTurnInformation.getCity()), playerTurnInformation.getName());

        return turnResponsePlayer;
    }
}

package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TurnServiceHelper {

    private TurnServiceHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static Player getNextActivePlayer(List<Player> playerList, Player activePlayer) {
        int pos = playerList.indexOf(activePlayer);
        return playerList.get((pos + 1) % playerList.size());
    }

    public static List<Player> getOtherPlayersOnTheCity(GameDTO gameDTO) {
        return gameDTO.getBoard().getPlayers()
                .stream()
                .filter(player -> !player.getName().equals(gameDTO.getTurnInformation().getActivePlayer().getName()))
                .filter(player -> player.getCity().equals(gameDTO.getTurnInformation().getActivePlayer().getCity()))
                .collect(Collectors.toList());
    }

    public static List<City> getCitiesWithResearchCenter(GameDTO gameDTO) {
        return gameDTO.getBoard().getBoardCities().stream().filter(City::getHasCenter).collect(Collectors.toList());
    }
}

package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EPIDEMIC;
import static java.util.Objects.isNull;

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

    public static boolean playerGetNewCardsIfIsNotEpidemic(PlayerQueue playerQueue, Player activePlayer) throws EndOfGameException {
        BaseCard cardToAdd = playerQueue.getCardFromPlayerQueue();
        if(isNull(cardToAdd)) {
            throw new EndOfGameException("Player Deck has no more cards");
        } else if(EPIDEMIC.name().equals(cardToAdd.getCardType())) {
            return false;
        }
        activePlayer.getListCard().add(cardToAdd);
        return true;
    }
}

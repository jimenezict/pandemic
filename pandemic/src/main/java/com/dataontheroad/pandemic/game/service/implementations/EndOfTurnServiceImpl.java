package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.interfaces.IEndOfTurnService;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.dataontheroad.pandemic.game.TurnServiceHelper.getNextActivePlayer;

@Service
public class EndOfTurnServiceImpl implements IEndOfTurnService {


    @Override
    public void getCardsFromPlayerDeck(PlayerQueue playerQueue, Player activePlayer) throws EndOfGameException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getCardsFromInfectionDeck(int numberOfInfections, InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck) throws EndOfGameException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNewTurnAndPlayer(List<Player> playerList, TurnInformation turnInformation) {
        Player player = getNextActivePlayer(playerList,
                turnInformation.getActivePlayer());
        turnInformation.setNewTurn(player);

    }
}

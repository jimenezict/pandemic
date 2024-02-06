package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;
import java.util.Set;

public interface IEndOfTurnService {

    boolean getCardsFromPlayerDeck(PlayerQueue playerQueue, Player activePlayer) throws EndOfGameException;

    void getCardsFromInfectionDeck(int numberOfInfections, InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck) throws EndOfGameException;

    void setNewTurnAndPlayer(List<Player> board, TurnInformation turnInformation);
}

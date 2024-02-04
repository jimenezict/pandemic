package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;

public interface IEpidemicService {
    boolean playerGetNewCardsIfIsNotEpidemicAsTimesAsManyTimesAsInfectionsCards(PlayerQueue playerQueue, Player activePlayer, int numberInfectionCard) throws EndOfGameException;

}

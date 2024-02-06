package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;

import java.util.Set;

public interface IDeckManagementService {

    City getCardFromBottomInfectionDesk(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck);

    City getCardFromTopInfectionDesk(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck);

    void shuffleAndAtToTheTopOfDeck(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck);


}

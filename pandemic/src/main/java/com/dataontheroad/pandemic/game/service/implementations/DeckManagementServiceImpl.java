package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.game.service.interfaces.IDeckManagementService;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

@Service
public class DeckManagementServiceImpl implements IDeckManagementService {
    @Override
    public City getCardFromBottomInfectionDesk(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck) {
        CityCard cityCard = infectionDeck.takeBottomCardOfDeck();
        infectionDiscardDeck.add(cityCard);
        return cityCard.getCity();
    }

    @Override
    public City getCardFromTopInfectionDesk(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck) {
        CityCard cityCard = infectionDeck.takeTopCardOfDeck();
        infectionDiscardDeck.add(cityCard);
        return cityCard.getCity();
    }

    @Override
    public void shuffleAndAtToTheTopOfDeck(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck) {
        if(!infectionDiscardDeck.isEmpty()) {
            ArrayList<CityCard> infectionDiscardDeckSortedLocal = new ArrayList<>(infectionDiscardDeck);
            Collections.shuffle(infectionDiscardDeckSortedLocal);
            infectionDiscardDeckSortedLocal.forEach(cardDiscarded -> infectionDeck.getDeck().addFirst(cardDiscarded));
            infectionDiscardDeck.clear();
        }
    }
}

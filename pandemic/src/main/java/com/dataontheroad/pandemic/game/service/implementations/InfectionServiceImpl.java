package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.game.service.interfaces.IInfectionService;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class InfectionServiceImpl implements IInfectionService {

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

    @Override
    public VirusType infectCity(City cityFromBoardList) {
        if(cityFromBoardList.getVirusBoxes().size() < 3) {
            cityFromBoardList.addVirusBoxes(cityFromBoardList.getVirus());
            return null;
        }
        return cityFromBoardList.getVirus();
    }

    @Override
    public boolean canCityBeInfected(List<Virus> virusList, List<Player> players) {
        return false;
    }

    @Override
    public void spreadOutbreak(List<Player> players, List<City> nodeCityConnection) {

    }
}

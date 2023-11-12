package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.List;
import java.util.Set;


public interface IInfectionService {

    City getCardFromBottomInfectionDesk(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck);

    City getCardFromTopInfectionDesk(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck);

    void shuffleAndAtToTheTopOfDeck(InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck);

    VirusType infectCityAndReturnCityVirusTypeIfOverpassOutbreak(City cityFromBoardList);

    boolean canCityBeInfected(City cityToInfect, List<Virus> virusList, List<Player> players);

    void spreadOutbreak(List<Player> players, List<City> nodeCityConnection);
}

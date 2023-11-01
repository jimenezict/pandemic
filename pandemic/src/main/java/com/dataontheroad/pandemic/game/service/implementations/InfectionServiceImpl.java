package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.game.service.interfaces.IInfectionService;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import com.dataontheroad.pandemic.model.player.MedicPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.QuarantinePlayer;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.MEDIC_NAME;
import static com.dataontheroad.pandemic.constants.LiteralsPlayers.QUARANTINE_NAME;
import static java.util.Objects.isNull;

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
    public boolean canCityBeInfected(City cityToInfect, List<Virus> virusList, List<Player> players) {
        Virus cityVirus = virusList.stream().filter(virusLocal -> virusLocal.getVirusType().equals(cityToInfect.getVirus())).findFirst().get();
        // if virus has been eradicated cannot be extended
        if(cityVirus.getEradicated())
            return false;

        // quarantine player prevent the propagation on its city and the connected ones
        QuarantinePlayer quarantinePlayer = (QuarantinePlayer) players.stream().filter(player -> QUARANTINE_NAME.equals(player.getName())).findFirst().orElse(null);
        if(!isNull(quarantinePlayer)) {
            return !(quarantinePlayer.getCity().getNodeCityConnection().contains(cityToInfect) || quarantinePlayer.getCity().equals(cityToInfect));
        }

        // if research is done, virus cannot be extended to the city with the medic
        MedicPlayer medicPlayer = (MedicPlayer) players.stream().filter(player -> MEDIC_NAME.equals(player.getName())).findFirst().orElse(null);
        if(!isNull(medicPlayer)) {
            return !(medicPlayer.getCity().equals(cityToInfect) && cityVirus.getCureDiscovered());
        }

        return true;
    }

    @Override
    public void spreadOutbreak(List<Player> players, List<City> nodeCityConnection) {

    }
}

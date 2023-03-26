package com.dataontheroad.pandemic.board.cards;

import com.dataontheroad.pandemic.board.city.CityFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.board.cards.CityCard.createCityCard;
import static com.dataontheroad.pandemic.board.cards.CityCard.createInfectionCard;

public class DeckCardFactory {

    public List<CityCard> createInfectionDeck() {
        List<CityCard> infectionDeck = CityFactory.createCityList().stream().map(city -> createInfectionCard(city)).collect(Collectors.toList());
        Collections.shuffle(infectionDeck);
        return infectionDeck;
    }

    public List<CityCard> createCityDeck(int numberOfEpidemicCards) {
        List<CityCard> cityDeck = CityFactory.createCityList().stream().map(city -> createCityCard(city)).collect(Collectors.toList());
        cityDeck.addAll(createEpidemicCards(numberOfEpidemicCards));
        cityDeck.addAll(listSpecialAction());
        Collections.shuffle(cityDeck);
        return cityDeck;
    }

    public List<CityCard> listSpecialAction() {
        return null;
    }

    public List<CityCard> createEpidemicCards(int numberOfEpidemicCards) {
        return null;
    }
}

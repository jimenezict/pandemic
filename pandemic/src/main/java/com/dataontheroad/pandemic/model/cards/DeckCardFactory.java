package com.dataontheroad.pandemic.model.cards;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.cards.model.EpidemicCard;
import com.dataontheroad.pandemic.model.city.CityFactory;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;

import java.util.*;
import java.util.stream.Collectors;

public class DeckCardFactory {

    private DeckCardFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static InfectionDeck createInfectionDeck() {
        List<CityCard> infectionDeck = CityFactory.createCityList().stream().map(CityCard::createInfectionCard).collect(Collectors.toList());
        Collections.shuffle(infectionDeck);
        return new InfectionDeck(infectionDeck);
    }

    public static List<BaseCard> createCityDeck(int numberOfEpidemicCards) {
        List<BaseCard> cityDeck = CityFactory.createCityList().stream().map(CityCard::createCityCard).collect(Collectors.toList());
        cityDeck.addAll(createEpidemicCards(numberOfEpidemicCards));
        cityDeck.addAll(listSpecialAction());
        Collections.shuffle(cityDeck);
        return cityDeck;
    }

    public static List<BaseCard> createCityDeck() {
        List<BaseCard> cityDeck = CityFactory.createCityList().stream().map(CityCard::createCityCard).collect(Collectors.toList());
        cityDeck.addAll(listSpecialAction());
        return cityDeck;
    }

    public static List<BaseCard> listSpecialAction() {
        return new ArrayList<>();
    }

    public static List<EpidemicCard> createEpidemicCards(int numberOfEpidemicCards) {
        int i = 0;
        List<EpidemicCard> epidemicCards = new ArrayList<>();
        while(i < numberOfEpidemicCards) {
            epidemicCards.add(new EpidemicCard());
            i++;
        }
        return epidemicCards;
    }
}

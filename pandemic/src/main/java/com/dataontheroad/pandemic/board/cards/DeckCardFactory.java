package com.dataontheroad.pandemic.board.cards;

import com.dataontheroad.pandemic.board.cards.model.BaseCard;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.cards.model.EpidemicCard;
import com.dataontheroad.pandemic.board.city.CityFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeckCardFactory {

    private DeckCardFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static List<CityCard> createInfectionDeck() {
        List<CityCard> infectionDeck = CityFactory.createCityList().stream().map(CityCard::createInfectionCard).collect(Collectors.toList());
        Collections.shuffle(infectionDeck);
        return infectionDeck;
    }

    public static List<BaseCard> createCityDeck(int numberOfEpidemicCards) {
        List<BaseCard> cityDeck = CityFactory.createCityList().stream().map(CityCard::createCityCard).collect(Collectors.toList());
        cityDeck.addAll(createEpidemicCards(numberOfEpidemicCards));
        cityDeck.addAll(listSpecialAction());
        Collections.shuffle(cityDeck);
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

package com.dataontheroad.pandemic.model.decks;

import com.dataontheroad.pandemic.model.cards.model.CityCard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InfectionDeck {

    private Deque<CityCard> infectionDeck;

    public InfectionDeck(List<CityCard> infectionDeckList) {
        infectionDeck = new ArrayDeque<>(infectionDeckList);
    }

    public Deque getInfectionDeck() {
        return infectionDeck;
    }

    public List<CityCard> getInitialDrawInfectionDeck() {
        List<CityCard> infectionDeckAsList = new ArrayList<>(infectionDeck);
        List<CityCard> initialInfectionDraw = new ArrayList<> (infectionDeckAsList.subList(0, 9));
        infectionDeckAsList.subList(0, 9).clear();
        infectionDeck = new ArrayDeque<>(infectionDeckAsList);
        return initialInfectionDraw;
    }

    public CityCard takeTopCardOfDeck() {
        return infectionDeck.removeFirst();
    }

    public CityCard takeBottomCardOfDeck() {
        return infectionDeck.removeLast();
    }
}

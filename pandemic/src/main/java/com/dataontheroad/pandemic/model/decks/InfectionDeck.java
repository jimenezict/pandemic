package com.dataontheroad.pandemic.model.decks;

import com.dataontheroad.pandemic.model.cards.model.CityCard;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InfectionDeck implements Serializable {

    private Deque<CityCard> deck;

    public InfectionDeck(List<CityCard> infectionDeckList) {
        deck = new ArrayDeque<>(infectionDeckList);
    }

    public Deque<CityCard> getDeck() {
        return deck;
    }

    public List<CityCard> getInitialDrawInfectionDeck() {
        List<CityCard> infectionDeckAsList = new ArrayList<>(deck);
        List<CityCard> initialInfectionDraw = new ArrayList<> (infectionDeckAsList.subList(0, 9));
        infectionDeckAsList.subList(0, 9).clear();
        deck = new ArrayDeque<>(infectionDeckAsList);
        return initialInfectionDraw;
    }

    public CityCard takeTopCardOfDeck() {
        return deck.removeFirst();
    }

    public CityCard takeBottomCardOfDeck() {
        return deck.removeLast();
    }
}

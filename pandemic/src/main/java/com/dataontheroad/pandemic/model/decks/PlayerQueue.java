package com.dataontheroad.pandemic.model.decks;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;

import java.io.Serializable;
import java.util.*;

public class PlayerQueue implements Serializable {

    private Queue<BaseCard> queue;

    public PlayerQueue(List<BaseCard> playerQueueList) {
        queue = new LinkedList<>(playerQueueList);
    }

    public Queue<BaseCard> getQueue() {
        return queue;
    }

    public BaseCard getCardFromPlayerQueue() {
        return queue.poll();
    }

    public List<BaseCard> getInitialDrawCards(int numberOfPlayers) {
        List<BaseCard> playerCards = new ArrayList<> ();
        for(int i = 0; i < numberOfCardsToDraw(numberOfPlayers); i++) {
            playerCards.add(queue.remove());
        }
        return playerCards;
    }

    public void shuffleQueue() {
        List<BaseCard> baseCardList = (List<BaseCard>) queue;
        Collections.shuffle(baseCardList);
        queue = new LinkedList<>(baseCardList);
    }

    private int numberOfCardsToDraw(int numberOfPlayers) {
        switch(numberOfPlayers) {
            case 2:
                return 4;
            case 3:
                return 3;
            case 4:
                return 2;
            default:
                return -1;
        }
    }
}

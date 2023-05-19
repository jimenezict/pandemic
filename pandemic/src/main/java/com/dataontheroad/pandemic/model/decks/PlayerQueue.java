package com.dataontheroad.pandemic.model.decks;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;

import java.util.*;

public class PlayerQueue {

    private Queue<BaseCard> playerQueue;

    public PlayerQueue(List<BaseCard> playerQueueList) {
        playerQueue = new LinkedList<>(playerQueueList);
    }

    public Queue<BaseCard> getPlayerQueue() {
        return playerQueue;
    }

    public BaseCard getCardFromPlayerQueue() {
        return playerQueue.remove();
    }

    public List<BaseCard> getInitialDrawCards(int numberOfPlayers) {
        List<BaseCard> playerCards = new ArrayList<> ();
        for(int i = 0; i < numberOfCardsToDraw(numberOfPlayers); i++) {
            playerCards.add(playerQueue.remove());
        }
        return playerCards;
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

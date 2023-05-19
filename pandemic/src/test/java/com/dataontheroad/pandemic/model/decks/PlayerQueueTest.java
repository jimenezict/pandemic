package com.dataontheroad.pandemic.model.decks;

import com.dataontheroad.pandemic.model.board.Board;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createPlayerQueue;
import static org.junit.jupiter.api.Assertions.*;

class PlayerQueueTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void getInitialDrawCardsWhenNumberOfPlayersIsN() {
        PlayerQueue playerQueue = createPlayerQueue();
        int initialSize = playerQueue.getPlayerQueue().size();
        List<BaseCard> initialDrawCards = playerQueue.getInitialDrawCards(2);
        assertEquals(4, initialDrawCards.size());
        assertEquals(initialSize - 4, playerQueue.getPlayerQueue().size());
    }

    @Test
    void getCardFromPlayerQueue() {
        PlayerQueue playerQueue = createPlayerQueue();
        int initialSize = playerQueue.getPlayerQueue().size();
        BaseCard baseCard = playerQueue.getCardFromPlayerQueue();
        assertEquals(initialSize - 1, playerQueue.getPlayerQueue().size());
    }

}
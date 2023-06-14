package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.Game;
import com.dataontheroad.pandemic.game.persistence.model.GameHashMapSingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePersistenceOnHashMapTest {

    GamePersistenceOnHashMap underTest = new GamePersistenceOnHashMap();

    @Test
    public void createsThreeGames() {
        Game game1 = new Game();
        Game game2 = new Game();
        Game game3 = new Game();

        underTest.insertOrUpdateGame(game1);
        underTest.insertOrUpdateGame(game2);
        underTest.insertOrUpdateGame(game3);

        assertEquals(underTest.getGameById(game1.getUuid()), game1);
        assertEquals(underTest.getGameById(game2.getUuid()), game2);
        assertEquals(underTest.getGameById(game3.getUuid()), game3);

        Game game4 = new Game();

        assertNull(underTest.getGameById(game4.getUuid()));

        underTest.insertOrUpdateGame(game3);

        assertEquals(3, GameHashMapSingleton.getInstance().size());
    }

}
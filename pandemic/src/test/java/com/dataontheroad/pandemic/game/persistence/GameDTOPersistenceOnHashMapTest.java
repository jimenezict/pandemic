package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameHashMapSingleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameDTOPersistenceOnHashMapTest {

    GamePersistenceOnHashMap underTest = new GamePersistenceOnHashMap();

    @Test
    public void createsThreeGames() {
        GameDTO gameDTO1 = new GameDTO();
        GameDTO gameDTO2 = new GameDTO();
        GameDTO gameDTO3 = new GameDTO();

        underTest.insertOrUpdateGame(gameDTO1);
        underTest.insertOrUpdateGame(gameDTO2);
        underTest.insertOrUpdateGame(gameDTO3);

        assertEquals(underTest.getGameById(gameDTO1.getUuid()), gameDTO1);
        assertEquals(underTest.getGameById(gameDTO2.getUuid()), gameDTO2);
        assertEquals(underTest.getGameById(gameDTO3.getUuid()), gameDTO3);

        GameDTO gameDTO4 = new GameDTO();

        assertNull(underTest.getGameById(gameDTO4.getUuid()));

        underTest.insertOrUpdateGame(gameDTO3);

        assertEquals(3, GameHashMapSingleton.getInstance().size());
    }

}
package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameHashMapSingleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameDTOPersistenceOnHashMapTest {

    GamePersistenceOnHashMap underTest = new GamePersistenceOnHashMap();

    @Test
    void createsThreeGames() throws Exception {
        GameDTO gameDTO1 = new GameDTO(4);
        GameDTO gameDTO2 = new GameDTO(4);
        GameDTO gameDTO3 = new GameDTO(4);

        underTest.insertOrUpdateGame(gameDTO1);
        underTest.insertOrUpdateGame(gameDTO2);
        underTest.insertOrUpdateGame(gameDTO3);

        assertEquals(underTest.getGameById(gameDTO1.getUuid()), gameDTO1);
        assertEquals(underTest.getGameById(gameDTO2.getUuid()), gameDTO2);
        assertEquals(underTest.getGameById(gameDTO3.getUuid()), gameDTO3);

        GameDTO gameDTO4 = new GameDTO(4);

        assertNull(underTest.getGameById(gameDTO4.getUuid()));

        underTest.insertOrUpdateGame(gameDTO3);

        assertEquals(3, GameHashMapSingleton.getInstance().size());
    }

}
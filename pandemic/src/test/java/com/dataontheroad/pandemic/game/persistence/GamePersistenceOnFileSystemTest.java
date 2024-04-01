package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePersistenceOnFileSystemTest {

    GamePersistenceOnFileSystem underTest = new GamePersistenceOnFileSystem();

    @AfterEach
    public void removeAllPNDfiles() {

    }

    @Test
    void serializeGameDTOAndRead() throws GameExecutionException {
        GameDTO gameDTO = new GameDTO(2);
        underTest.insertOrUpdateGame(gameDTO);
        GameDTO gameDTOfromFile = underTest.getGameById(gameDTO.getUuid());
        assertEquals(gameDTO.getUuid(), gameDTOfromFile.getUuid());
    }


}
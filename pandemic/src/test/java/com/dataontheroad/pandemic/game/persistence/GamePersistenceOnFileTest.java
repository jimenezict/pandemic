package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePersistenceOnFileTest {

    GamePersistenceOnFile underTest = new GamePersistenceOnFile();

    @Test
    void createsThreeGames() throws Exception {
        GameDTO gameDTO1 = new GameDTO(4);
        GameDTO gameDTO2 = new GameDTO(4);
        GameDTO gameDTO3 = new GameDTO(4);

        underTest.insertOrUpdateGame(gameDTO1);
        underTest.insertOrUpdateGame(gameDTO2);
        underTest.insertOrUpdateGame(gameDTO3);

        assertEquals(underTest.getGameById(gameDTO1.getUuid()).getUuid(), gameDTO1.getUuid());
        assertEquals(underTest.getGameById(gameDTO2.getUuid()).getUuid(), gameDTO2.getUuid());
        assertEquals(underTest.getGameById(gameDTO3.getUuid()).getUuid(), gameDTO3.getUuid());

        GameDTO gameDTO4 = new GameDTO(4);

        assertNull(underTest.getGameById(gameDTO4.getUuid()));
    }

    @Test
    void createsAndRecoverAGame() throws Exception {
        GameDTO gameDTO = new GameDTO(4);
        underTest.insertOrUpdateGame(gameDTO);

        GameDTO recoveredGameDTO = underTest.getGameById(gameDTO.getUuid());
        assertEquals(gameDTO.getUuid(), recoveredGameDTO.getUuid());
        assertEquals(gameDTO.getLocalDate(), recoveredGameDTO.getLocalDate());
        assertEquals(gameDTO.getUpdateDateTime(), recoveredGameDTO.getUpdateDateTime());

        assertEquals(gameDTO.getTurnInformation().getMissingTurns(), recoveredGameDTO.getTurnInformation().getMissingTurns());
        assertEquals(gameDTO.getTurnInformation().canDoNextActionAndReduceMissingTurns(), recoveredGameDTO.getTurnInformation().canDoNextActionAndReduceMissingTurns());
        assertEquals(gameDTO.getTurnInformation().getMissingTurns(), recoveredGameDTO.getTurnInformation().getMissingTurns());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getDescription(), recoveredGameDTO.getTurnInformation().getActivePlayer().getDescription());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getCity(), recoveredGameDTO.getTurnInformation().getActivePlayer().getCity());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getListCard().size(), recoveredGameDTO.getTurnInformation().getActivePlayer().getListCard().size());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getDriveFerry(), recoveredGameDTO.getTurnInformation().getActivePlayer().getDriveFerry());

        assertEquals(gameDTO.getBoard().getBoardCities().size(), recoveredGameDTO.getBoard().getBoardCities().size());
        assertEquals(gameDTO.getBoard().getInfectionDeck().getDeck().size(), recoveredGameDTO.getBoard().getInfectionDeck().getDeck().size());
        assertEquals(gameDTO.getBoard().getBoardCities().size(), recoveredGameDTO.getBoard().getBoardCities().size());
        assertEquals(gameDTO.getBoard().getBoardCities().get(0).getName(), recoveredGameDTO.getBoard().getBoardCities().get(0).getName());
        assertEquals(gameDTO.getBoard().getPlayers().size(), recoveredGameDTO.getBoard().getPlayers().size());
        assertEquals(gameDTO.getBoard().getPlayers().get(0).getName(), recoveredGameDTO.getBoard().getPlayers().get(0).getName());
    }


}
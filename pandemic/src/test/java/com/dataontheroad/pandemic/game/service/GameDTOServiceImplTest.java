package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.GameResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameDTOServiceImplTest {

    @InjectMocks
    GameServiceImpl gameService;

    @Mock
    GamePersistenceOnHashMap gamePersistence;

    @Captor
    ArgumentCaptor<GameDTO> gameCaptor;

    @Test
    void createGame() {
        UUID uuid = gameService.createGame(4,2);

        assertTrue(!isNull(uuid));
        verify(gamePersistence).insertOrUpdateGame(any());
        verify(gamePersistence).insertOrUpdateGame(gameCaptor.capture());
        GameDTO gameDTO = gameCaptor.getValue();
        assertEquals(uuid, gameDTO.getUuid());
        assertTrue(!isNull(gameDTO.getLocalDate()));
        assertTrue(!isNull(gameDTO.getUpdateDateTime()));
    }

    @Test
    void getGameById() {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setUpdateDateTime(LocalDateTime.now());

        when(gamePersistence.getGameById(gameDTO.getUuid())).thenReturn(gameDTO);

        GameResponseDTO testGameDTO = gameService.getGameById(gameDTO.getUuid());
        assertEquals(gameDTO.getUuid(), testGameDTO.getUuid());
    }

}
package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;import com.dataontheroad.pandemic.game.persistence.model.Game;
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
class GameServiceImplTest {

    @InjectMocks
    GameServiceImpl gameService;

    @Mock
    GamePersistenceOnHashMap gamePersistence;

    @Captor
    ArgumentCaptor<Game> gameCaptor;

    @Test
    void createGame() {
        UUID uuid = gameService.createGame(4,2);

        assertTrue(!isNull(uuid));
        verify(gamePersistence).insertOrUpdateGame(any());
        verify(gamePersistence).insertOrUpdateGame(gameCaptor.capture());
        Game game = gameCaptor.getValue();
        assertEquals(uuid, game.getUuid());
        assertTrue(!isNull(game.getLocalDate()));
        assertTrue(!isNull(game.getUpdateDateTime()));
    }

    @Test
    void getGameById() {
        Game game = new Game();
        game.setUpdateDateTime(LocalDateTime.now());

        when(gamePersistence.getGameById(game.getUuid())).thenReturn(game);

        Game testGame = gameService.getGameById(game.getUuid());
        assertEquals(game.getUuid(), testGame.getUuid());
    }

}
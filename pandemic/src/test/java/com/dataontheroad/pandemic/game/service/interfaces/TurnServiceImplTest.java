package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl;
import com.dataontheroad.pandemic.model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.dataontheroad.pandemic.game.service.implementations.TurnServiceImpl.getOtherPlayersOnTheCity;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TurnServiceImplTest {
    @InjectMocks
    TurnServiceImpl turnService;

    @Mock
    GamePersistenceOnHashMap gamePersistence;

    private static UUID uuid = randomUUID();

    @Test
    void getTurnServiceInformation_returnNull() {
        when(gamePersistence.getGameById(any())).thenReturn(null);
        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(uuid);
        assertNull(turnResponseDTO);
    }

    @Test
    void getTurnServiceInformation_returnGameDTO() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        when(gamePersistence.getGameById(any())).thenReturn(gameDTO);

        TurnResponseDTO turnResponseDTO = turnService.getTurnServiceInformation(uuid);
        assertEquals(4, turnResponseDTO.getMissingTurns());
        assertEquals(4, gameDTO.getTurnInformation().getMissingTurns());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getName(), turnResponseDTO.getActivePlayer().getName());
        assertEquals(gameDTO.getTurnInformation().getActivePlayer().getCity().getName(), turnResponseDTO.getActivePlayer().getLocation().getName());
        assertFalse(turnResponseDTO.getActionList().isEmpty());
        assertFalse(turnResponseDTO.getActivePlayer().getListCard().isEmpty());
    }

    @Test
    void getOtherPlayersOnTheCity_shouldReturn2() throws Exception {
        GameDTO gameDTO = new GameDTO(3);
        List<Player> playerList = getOtherPlayersOnTheCity(gameDTO);
        assertEquals(2, playerList.size());
    }

}
package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.GameResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvertGamesDTOTest {

    @Test
    void convertGameDTO() {
        GameDTO gameDTO = new GameDTO();
        GameResponseDTO gameResponseDTO = ConvertGamesDTO.convertGameDTO(gameDTO);
        assertEquals(gameDTO.getUuid(), gameResponseDTO.getUuid());
    }

}
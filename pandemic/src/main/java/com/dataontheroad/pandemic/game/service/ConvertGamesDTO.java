package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.GameResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;

public class ConvertGamesDTO {

    public static GameResponseDTO convertGameDTO(GameDTO gameDTO) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO(gameDTO.getUuid());
        return gameResponseDTO;
    }
}

package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.GamePersistenceOnHashMap;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class TurnServiceImpl implements ITurnService {

    @Autowired
    GamePersistenceOnHashMap gamePersistence;

    @Override
    public TurnResponseDTO getTurnServiceInformation(UUID gameId) {
        return null;
    }
}

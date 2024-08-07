package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Qualifier("GamePersistenceOnFile")
public class GamePersistenceOnFile extends GamePersistenceAbstractClass {
    @Override
    public void insertOrUpdateGame(GameDTO gameDTO) {
        System.out.println("I am GamePersistenceOnFile");    }

    @Override
    public GameDTO getGameById(UUID uuid) {
        System.out.println("I am GamePersistenceOnFile");
        return null;
    }
}

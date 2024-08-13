package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import java.util.UUID;

@Repository
@Qualifier("gamePersistenceOnFile")
public class GamePersistenceOnFile extends GamePersistenceAbstractClass {

    private static final String basePath = "save";
    @Override
    public void insertOrUpdateGame(GameDTO gameDTO) {

        Path path = Paths.get(basePath,gameDTO.getUuid().toString() + ".pdm");

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            Files.createDirectories(path.getParent());

            oos.writeObject(gameDTO);
            oos.flush();

            Files.write(path, bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameDTO getGameById(UUID uuid) {
        Path path = Paths.get(basePath,uuid.toString() + ".pdm");

        try {
            byte[] bytes = Files.readAllBytes(path);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {

                return (GameDTO) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}

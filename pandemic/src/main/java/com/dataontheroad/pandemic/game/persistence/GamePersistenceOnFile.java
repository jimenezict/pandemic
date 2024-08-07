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
@Qualifier("GamePersistenceOnFile")
public class GamePersistenceOnFile extends GamePersistenceAbstractClass {
    @Override
    public void insertOrUpdateGame(GameDTO gameDTO) {

        Path path = Paths.get(gameDTO.getUuid().toString());
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(gameDTO);
            oos.flush();

            Files.write(path, bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("I am GamePersistenceOnFile");    }

    @Override
    public GameDTO getGameById(UUID uuid) {
        Path path = Paths.get(uuid.toString());

        try {
            byte[] bytes = Files.readAllBytes(path);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {

                return (GameDTO) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

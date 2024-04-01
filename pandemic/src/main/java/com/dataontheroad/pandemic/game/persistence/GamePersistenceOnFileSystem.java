package com.dataontheroad.pandemic.game.persistence;

import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static com.dataontheroad.pandemic.constants.LiteralGame.PANDEMIC_SAVED_FILES_EXCEPTION;
import static com.dataontheroad.pandemic.constants.LiteralGame.PANDEMIC_SAVED_FILES_FOLDER;

@Repository
public class GamePersistenceOnFileSystem implements IGamePersistence {

    @Override
    public void insertOrUpdateGame(GameDTO gameDTO) {

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(gameDTO.getUuid().toString() + PANDEMIC_SAVED_FILES_EXCEPTION),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING))) {
            oos.writeObject(gameDTO);
            oos.flush();
        } catch (IOException exception) {
            System.out.println("GAME NOT UPDATED");
        }
    }

    @Override
    public GameDTO getGameById(UUID uuid) {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(uuid.toString() + PANDEMIC_SAVED_FILES_EXCEPTION)))) {
            return (GameDTO) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
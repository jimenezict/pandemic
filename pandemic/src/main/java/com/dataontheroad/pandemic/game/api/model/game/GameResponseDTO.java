package com.dataontheroad.pandemic.game.api.model.game;

import com.dataontheroad.pandemic.model.virus.VirusType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameResponseDTO {

    private final UUID gameId;
    private LocalDateTime insertDateTime;
    private LocalDateTime updateDateTime;
    private List<GameResponsePlayer> gameResponsePlayerList;
    private HashMap<String, List<VirusType>> gameResponseCityList;

    public GameResponseDTO(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public LocalDateTime getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(LocalDateTime insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public List<GameResponsePlayer> getGameResponsePlayerList() {
        return gameResponsePlayerList;
    }

    public void setGameResponsePlayerList(List<GameResponsePlayer> gameResponsePlayerList) {
        this.gameResponsePlayerList = gameResponsePlayerList;
    }

    public HashMap<String, List<VirusType>> getGameResponseCityList() {
        return gameResponseCityList;
    }

    public void setGameResponseCityList(HashMap<String, List<VirusType>> gameResponseCityList) {
        this.gameResponseCityList = gameResponseCityList;
    }
}

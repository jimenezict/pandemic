package com.dataontheroad.pandemic.game.api.model.game;

import com.dataontheroad.pandemic.model.virus.VirusType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameResponseDTO {

    private final UUID gameId;
    private LocalDateTime insertDateTime;
    private LocalDateTime updateDateTime;
    private List<GameResponsePlayer> gameResponsePlayerList;
    private Map<String, List<VirusType>> gameResponseCityList;

    private List<String> citiesWithLab;

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

    public Map<String, List<VirusType>> getGameResponseCityList() {
        return gameResponseCityList;
    }

    public void setGameResponseCityList(Map<String, List<VirusType>> gameResponseCityList) {
        this.gameResponseCityList = gameResponseCityList;
    }

    public List<String> getCitiesWithLab() {
        return citiesWithLab;
    }

    public void setCitiesWithLab(List<String> citiesWithLab) {
        this.citiesWithLab = citiesWithLab;
    }
}

package com.dataontheroad.pandemic.game.service;

import com.dataontheroad.pandemic.game.api.model.GameResponseDTO;
import com.dataontheroad.pandemic.game.api.model.GameResponsePlayer;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertGamesDTO {

    public static GameResponseDTO convertGameDTO(GameDTO gameDTO) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO(gameDTO.getUuid());
        gameResponseDTO.setGameResponseCityList(buildGameResponseCityFromGameDTO(gameDTO.getBoard().getBoardCities()));
        gameResponseDTO.setGameResponsePlayerList(buildGameResponsePlayerFromGameDTO(gameDTO.getBoard().getPlayers()));
        gameResponseDTO.setInsertDateTime(gameDTO.getLocalDate());
        gameResponseDTO.setUpdateDateTime(gameDTO.getUpdateDateTime());
        return gameResponseDTO;
    }

    private static List<GameResponsePlayer> buildGameResponsePlayerFromGameDTO(List<Player> listPlayers) {
        return listPlayers.stream()
                .map(player ->
                        new GameResponsePlayer(player.getListCard(), player.getCity(), player.getName()))
                .collect(Collectors.toList());
    }

    private static HashMap<String, List<VirusType>> buildGameResponseCityFromGameDTO(List<City> cityList) {
        HashMap<String, List<VirusType>> responseCity = new HashMap<>();

        cityList.stream()
                .filter(city -> !city.getVirusBoxes().isEmpty())
                .forEach(city -> responseCity.put(city.getName(), city.getVirusBoxes()));

        return responseCity;
    }
}

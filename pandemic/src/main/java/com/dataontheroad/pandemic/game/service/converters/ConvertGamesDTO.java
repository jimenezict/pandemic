package com.dataontheroad.pandemic.game.service.converters;

import com.dataontheroad.pandemic.game.api.model.game.GameResponseCard;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseCity;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.game.api.model.game.GameResponsePlayer;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.CITY;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EVENT_ACTION;

public class ConvertGamesDTO {

    private ConvertGamesDTO() {
        throw new IllegalStateException("Utility class");
    }

    public static GameResponseDTO convertGameDTO(GameDTO gameDTO) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO(gameDTO.getUuid());
        gameResponseDTO.setGameResponseCityList(buildGameResponseCityFromGameDTO(gameDTO.getBoard().getBoardCities()));
        gameResponseDTO.setGameResponsePlayerList(buildGameResponsePlayerFromGameDTO(gameDTO.getBoard().getPlayers()));
        gameResponseDTO.setInsertDateTime(gameDTO.getLocalDate());
        gameResponseDTO.setUpdateDateTime(gameDTO.getUpdateDateTime());
        gameResponseDTO.setCitiesWithLab(buildGameResponseCitiesWithLab(gameDTO.getBoard().getBoardCities()));
        return gameResponseDTO;
    }

    private static List<GameResponsePlayer> buildGameResponsePlayerFromGameDTO(List<Player> listPlayers) {
        return listPlayers.stream()
                .map(player -> {

                    List<GameResponseCard> gameResponseCards = player.getListCard().stream().map(card -> {
                        switch (card.getCardType()) {
                            case CITY:
                                CityCard cityCard = (CityCard) card;
                                return new GameResponseCard(CITY, cityCard.getCity().getName());
                            case EVENT_ACTION:
                                SpecialCard specialCard = (SpecialCard) card;
                                return new GameResponseCard(EVENT_ACTION, specialCard.getEventName());
                            default:
                                return null;
                        }
                    }).collect(Collectors.toList());
                    return new GameResponsePlayer(gameResponseCards, new GameResponseCity(player.getCity()), player.getName());
                })
                .collect(Collectors.toList());
    }

    private static HashMap<String, List<VirusType>> buildGameResponseCityFromGameDTO(List<City> cityList) {
        HashMap<String, List<VirusType>> responseCity = new HashMap<>();

        cityList.stream()
                .filter(city -> !city.getVirusBoxes().isEmpty())
                .forEach(city -> responseCity.put(city.getName(), city.getVirusBoxes()));

        return responseCity;
    }

    private static List<String> buildGameResponseCitiesWithLab(List<City> cityList) {
        return cityList.stream()
                .filter(City::getHasCenter)
                .map(City::getName)
                .collect(Collectors.toList());
    }
}

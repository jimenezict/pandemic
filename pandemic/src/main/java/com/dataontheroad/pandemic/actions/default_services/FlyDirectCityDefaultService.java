package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.FlyDirectAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.FLYDIRECT_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.CITY;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;

public class FlyDirectCityDefaultService {

    public boolean isDoable(Player player, City destination) {
        return player.getListCard().contains(createCityCard(destination));
    }

    public List<Action> returnAvailableActions(Player player, List<City> boardCities) {
        return player.getListCard().stream()
                .filter(card -> CITY.equals(card.getCardType()))
                .filter(destination -> !player.getCity().equals(((CityCard) destination).getCity()))
                .map(destination -> {
                    City boardCity = getBoardCity(boardCities, ((CityCard) destination).getCity());
                    return new FlyDirectAction(player, boardCity);
                })
                .collect(Collectors.toList());
    }

    private static City getBoardCity(List<City> boardCities, City destination) {
        Optional<City> optional = boardCities.stream().filter(city -> city.equals(destination)).findFirst();
        if(optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public void doAction(Player player, City destination) throws ActionException {
        if(!player.getListCard().contains(createCityCard(destination))) {
            throw new ActionException(ActionsType.FLYDIRECT, FLYDIRECT_ERROR_NO_CARD);
        }
        player.setCity(destination);
        player.getListCard().remove(createCityCard(destination));
    }
}

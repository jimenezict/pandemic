package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DiscoverCureAction;
import com.dataontheroad.pandemic.actions.default_services.DiscoverCureDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.CITY;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class DiscoverCureScientist extends DiscoverCureDefaultService {

    private static DiscoverCureScientist discoverCureDefaultService;

    private DiscoverCureScientist() {
        super();
    }

    public static DiscoverCureScientist getInstance() {
        if(isNull(discoverCureDefaultService)) {
            discoverCureDefaultService = new DiscoverCureScientist();
        }
        return discoverCureDefaultService;
    }

    public static boolean isDoable(Player player, Virus virus) {
        return player.getCity().getHasCenter()
                && !virus.getCureDiscovered()
                && playerHasEnoughCars(player, virus.getVirusType());
    }

    public static void doAction(Player player, Virus virus) throws ActionException {
        if(!player.getCity().getHasCenter()) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_NO_RESEARCH_STATION);
        } else if (virus.getCureDiscovered()) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_CURE_DISCOVERED);
        } else if (!playerHasEnoughCars(player, virus.getVirusType())) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_NO_CARD);
        }

        virus.cureHasBeenDiscovered();
    }

    public static boolean playerHasEnoughCars(Player player, VirusType virusType) {
        return player.getListCard().stream()
                .filter(card -> CITY.equals(card.getCardType()))
                .filter(card -> ((CityCard) card).getVirus().equals(virusType)).count() == 4;
    }

}

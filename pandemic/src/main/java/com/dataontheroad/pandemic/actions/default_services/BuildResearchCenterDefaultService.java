package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.BuildResearchCenterAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsHelper.playerHasCardForHisPosition;
import static com.dataontheroad.pandemic.actions.ActionsHelper.playerRemoveCardFromDeck;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_CENTER_CREATED;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static java.util.Objects.isNull;

public class BuildResearchCenterDefaultService {

    private static BuildResearchCenterDefaultService buildResearchCenterDefaultService;

    private BuildResearchCenterDefaultService() {
    }

    public static BuildResearchCenterDefaultService getInstance() {
        if(isNull(buildResearchCenterDefaultService)) {
            buildResearchCenterDefaultService = new BuildResearchCenterDefaultService();
        }
        return buildResearchCenterDefaultService;
    }

    public static boolean isDoable(Player player) {
        City position = player.getCity();
        return !position.getHasCenter() && playerHasCardForHisPosition(player, position);
    }

    public static List<Action> returnAvailableActions(Player player) {
        return isDoable(player)? new ArrayList<>(Arrays.asList(new BuildResearchCenterAction(player))) : new ArrayList<>();
    }

    public static void doAction(Player player) throws ActionException {
        City position = player.getCity();
        if(position.getHasCenter()) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_CENTER_CREATED);
        } else if (!playerHasCardForHisPosition(player, position)) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_NO_CARD);
        }
        position.setHasCenter(Boolean.TRUE);
        playerRemoveCardFromDeck(player, createCityCard(position));
    }
}

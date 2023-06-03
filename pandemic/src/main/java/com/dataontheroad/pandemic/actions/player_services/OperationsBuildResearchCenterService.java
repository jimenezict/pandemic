package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.BuildResearchCenterAction;
import com.dataontheroad.pandemic.actions.default_services.BuildResearchCenterDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsHelper.playerHasCardForHisLocation;
import static com.dataontheroad.pandemic.actions.ActionsHelper.playerRemoveCardFromHand;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_CENTER_CREATED;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static java.util.Objects.isNull;

public class OperationsBuildResearchCenterService extends BuildResearchCenterDefaultService {

    private static OperationsBuildResearchCenterService operationsBuildResearchCenterService;

    private OperationsBuildResearchCenterService() {
        super();
    }

    public static OperationsBuildResearchCenterService getInstance() {
        if(isNull(operationsBuildResearchCenterService)) {
            operationsBuildResearchCenterService = new OperationsBuildResearchCenterService();
        }
        return operationsBuildResearchCenterService;
    }

    public static boolean isDoable(Player player) {
        return !player.getCity().getHasCenter();
    }


    public static List<Action> returnAvailableActions(Player player) {
        return isDoable(player)? new ArrayList<>(Arrays.asList(new BuildResearchCenterAction(player))) : new ArrayList<>();
    }


    public static void doAction(Player player) throws ActionException {
        City position = player.getCity();
        if(position.getHasCenter()) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_CENTER_CREATED);
        } else if (!playerHasCardForHisLocation(player, position)) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_NO_CARD);
        }
        position.setHasCenter(Boolean.TRUE);
    }
}

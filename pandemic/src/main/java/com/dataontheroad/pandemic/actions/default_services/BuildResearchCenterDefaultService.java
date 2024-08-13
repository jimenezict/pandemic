package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.BuildResearchCenterAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsHelper.playerHasCardForHisLocation;
import static com.dataontheroad.pandemic.actions.ActionsHelper.playerRemoveCardFromHand;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_CENTER_CREATED;
import static com.dataontheroad.pandemic.constants.LiteralsAction.BUILDRESEARCHSTATION_ERROR_NO_CARD;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static java.util.Objects.isNull;

/**
 * Default Service for Building Research Center. A research center can be created when player is on a city
 * without research center, and holds the card of the city, and had not over pass the maximum allowed research centers
 * on the board.
 * <p>
 * There are exceptions over the default behave when:
 * * Is used the action card .....
 * * Player ..... can create research centers with no need of a city card.
 */
public class BuildResearchCenterDefaultService {

    private static BuildResearchCenterDefaultService buildResearchCenterDefaultService;

    protected BuildResearchCenterDefaultService() {

    }

    public static BuildResearchCenterDefaultService getInstance() {
        if(isNull(buildResearchCenterDefaultService)) {
            buildResearchCenterDefaultService = new BuildResearchCenterDefaultService();
        }
        return buildResearchCenterDefaultService;
    }

    /**
     * Action will be doable when there is no research center and player has the city card of its location
     *
     * @param player Player who executes the action
     * @return returns true is a research center can be constructed
     */
    public boolean isDoable(Player player) {
        City location = player.getCity();
        return !location.getHasCenter() && playerHasCardForHisLocation(player, location);
    }

    /**
     *
     * @param player Player who executes the action
     * @return Returns an action in a list if player can build a research center
     */
    public List<Action> returnAvailableActions(Player player) {
        return isDoable(player)? new ArrayList<>(Collections.singletonList(new BuildResearchCenterAction(player))) : new ArrayList<>();
    }

    /**
     * Place a research center on the city and remove the card from the player hand
     *
     * @param player the player
     * @throws ActionException the action exception
     */
    public void doAction(Player player) throws ActionException {
        City position = player.getCity();
        if(position.getHasCenter()) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_CENTER_CREATED);
        } else if (!playerHasCardForHisLocation(player, position)) {
            throw new ActionException(ActionsType.BUILDRESEARCHSTATION, BUILDRESEARCHSTATION_ERROR_NO_CARD);
        }
        position.setHasCenter(Boolean.TRUE);
        playerRemoveCardFromHand(player, createCityCard(position));
    }
}

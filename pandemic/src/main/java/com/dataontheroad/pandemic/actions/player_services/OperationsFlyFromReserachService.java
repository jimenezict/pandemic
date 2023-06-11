package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.player_actions.FlyFromResearchCenterAnywhereAction;
import com.dataontheroad.pandemic.actions.default_services.BuildResearchCenterDefaultService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class OperationsFlyFromReserachService extends BuildResearchCenterDefaultService {

    private static OperationsFlyFromReserachService operationsFlyFromReserachService;

    private OperationsFlyFromReserachService() {
        super();
    }

    public static OperationsFlyFromReserachService getInstance() {
        if(isNull(operationsFlyFromReserachService)) {
            operationsFlyFromReserachService = new OperationsFlyFromReserachService();
        }
        return operationsFlyFromReserachService;
    }

    public static boolean isDoable(OperationsPlayer player) {
        return player.getCity().getHasCenter()
                && player.canPlayerExecuteActionThisTurn()
                && !isEmpty(player.getListCard());
    }


    public static List<Action> returnAvailableActions(OperationsPlayer player) {
        return isDoable(player)? new ArrayList<>(Arrays.asList(new FlyFromResearchCenterAnywhereAction(player))) : new ArrayList<>();
    }


    public static void doAction(OperationsPlayer player, City destination, BaseCard discardCard) throws ActionException {
        City position = player.getCity();

        if(!position.getHasCenter()) {
            throw new ActionException(ActionsType.OPERATION_FLY, OPERATIONFLY_ERROR_CITYWITHOUTRESEARCHCENTER);
        }  else if (!player.canPlayerExecuteActionThisTurn()) {
            throw new ActionException(ActionsType.OPERATION_FLY, OPERATIONFLY_ERROR_PLAYERHASALREADYFLYTHISTURN);
        } else if (isEmpty(player.getListCard())) {
            throw new ActionException(ActionsType.OPERATION_FLY, OPERATIONFLY_ERROR_PLAYERHASNOCARD);
        }
        player.setCity(destination);
        player.getListCard().remove(discardCard);
        player.actionHasBeenExecuted();
    }
}

package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.ICommuting;
import com.dataontheroad.pandemic.actions.player_services.OperationsFlyFromReserachService;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;

import static com.dataontheroad.pandemic.constants.LiteralsAction.OPERATIONFLY_ACTION;

public class FlyFromResearchCenterAnywhereAction extends Action implements ICommuting {
    City destination;
    BaseCard discardCard;

    public FlyFromResearchCenterAnywhereAction(OperationsPlayer player) {
        super(ActionsType.OPERATION_FLY, player);
    }

    @Override
    public String actionPrompt() {
        return OPERATIONFLY_ACTION;
    }

    @Override
    public void execute() throws ActionException {
        OperationsPlayer operationsPlayer = (OperationsPlayer) getPlayer();
        OperationsFlyFromReserachService specialAction = operationsPlayer.specialActionService();
        specialAction.doAction(operationsPlayer, destination, discardCard);
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    @Override
    public City getDestination() {
        return destination;
    }

    public void setDiscardCard(BaseCard discardCard) {
        this.discardCard = discardCard;
    }
}

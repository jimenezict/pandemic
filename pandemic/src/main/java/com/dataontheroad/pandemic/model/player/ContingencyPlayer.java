package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.player_services.ContingencyPlannerService;
import com.dataontheroad.pandemic.model.cards.model.special_card.SpecialCard;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class ContingencyPlayer extends Player {

    SpecialCard extraEventCard = null;
    public ContingencyPlayer() {
        super();
        color = CONTINGENCY_COLOR;
        name = CONTINGENCY_NAME;
        description = CONTINGENCY_DESCRIPTION;
        numOfCardsForDiscoveringCure = 5;
    }

    public SpecialCard getExtraEventCard() {
        return extraEventCard;
    }

    public ContingencyPlannerService specialActionService() {
        return ContingencyPlannerService.getInstance();
    }

}

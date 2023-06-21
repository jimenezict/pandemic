package com.dataontheroad.pandemic.game.api.model;

import com.dataontheroad.pandemic.model.cards.CardTypeEnum;

public class GameResponseCard {

    private final CardTypeEnum eventAction;
    private final String eventName;

    public GameResponseCard(CardTypeEnum eventAction, String eventName) {
        this.eventAction = eventAction;
        this.eventName = eventName;
    }

    public CardTypeEnum getEventAction() {
        return eventAction;
    }

    public String getEventName() {
        return eventName;
    }
}

package com.dataontheroad.pandemic.model.cards.model.special_card;

import com.dataontheroad.pandemic.model.cards.model.BaseCard;

import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EVENT_ACTION;

public abstract class SpecialCard extends BaseCard {

    private String eventName;

    private String eventDescription;

    protected SpecialCard() {
        super(EVENT_ACTION);
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}

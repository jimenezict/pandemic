package com.dataontheroad.pandemic.board.cards.model.special_card;

import com.dataontheroad.pandemic.board.cards.model.BaseCard;

import static com.dataontheroad.pandemic.board.cards.CardTypeEnum.SPECIAL_ACTION;

public abstract class SpecialCard extends BaseCard {

    private String eventName;

    private String eventDescription;

    protected SpecialCard() {
        super(SPECIAL_ACTION);
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}

package com.dataontheroad.pandemic.model.cards.model;

import com.dataontheroad.pandemic.model.cards.CardTypeEnum;

public abstract class BaseCard {

    protected final CardTypeEnum cardTypeEnum;

    protected BaseCard(CardTypeEnum cardTypeEnum) {
        this.cardTypeEnum = cardTypeEnum;
    }

    public CardTypeEnum getCardType() {
        return cardTypeEnum;
    }
}

package com.dataontheroad.pandemic.model.cards.model;

import com.dataontheroad.pandemic.model.cards.CardTypeEnum;

import java.io.Serializable;

public abstract class BaseCard implements Serializable {

    protected final CardTypeEnum cardTypeEnum;

    protected BaseCard(CardTypeEnum cardTypeEnum) {
        this.cardTypeEnum = cardTypeEnum;
    }

    public CardTypeEnum getCardType() {
        return cardTypeEnum;
    }
}

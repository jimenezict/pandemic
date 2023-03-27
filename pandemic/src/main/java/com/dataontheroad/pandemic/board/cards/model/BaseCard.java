package com.dataontheroad.pandemic.board.cards.model;

import com.dataontheroad.pandemic.board.cards.CardTypeEnum;

public abstract class BaseCard {

    private final CardTypeEnum cardTypeEnum;

    public BaseCard(CardTypeEnum cardTypeEnum) {
        this.cardTypeEnum = cardTypeEnum;
    }

    public CardTypeEnum getCardType() {
        return cardTypeEnum;
    }
}

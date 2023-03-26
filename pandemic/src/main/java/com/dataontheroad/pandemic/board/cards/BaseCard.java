package com.dataontheroad.pandemic.board.cards;

import com.dataontheroad.pandemic.board.model.enums.VirusType;

public abstract class BaseCard {

    private final CardType cardType;

    public BaseCard(CardType cardType) {
        this.cardType = cardType;
    }

    public CardType getCardType() {
        return cardType;
    }
}

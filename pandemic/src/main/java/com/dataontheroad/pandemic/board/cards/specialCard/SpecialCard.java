package com.dataontheroad.pandemic.board.cards.specialCard;

import com.dataontheroad.pandemic.board.cards.BaseCard;

import static com.dataontheroad.pandemic.board.cards.CardType.SPECIAL_ACTION;

public abstract class SpecialCard extends BaseCard {

    private String eventName;

    private String eventDescription;

    public SpecialCard() {
        super(SPECIAL_ACTION);
    }
}

package com.dataontheroad.pandemic.board.cards.model;

import static com.dataontheroad.pandemic.board.cards.CardTypeEnum.EPIDEMIC;
import static com.dataontheroad.pandemic.constants.LiteralsCard.*;

public class EpidemicCard extends BaseCard{


    public EpidemicCard() {
        super(EPIDEMIC);
    }

    public String getIncreaseText() {
        return INCREASE_TEXT;
    }

    public String getInfectText() {
        return INFECT_TEXT;
    }

    public String getIntensifyText() {
        return INTENSIFY_TEXT;
    }
}

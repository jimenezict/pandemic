package com.dataontheroad.pandemic.model.cards.model;

import java.io.Serializable;

import static com.dataontheroad.pandemic.constants.LiteralsCard.*;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EPIDEMIC;

public class EpidemicCard extends BaseCard implements Serializable {


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

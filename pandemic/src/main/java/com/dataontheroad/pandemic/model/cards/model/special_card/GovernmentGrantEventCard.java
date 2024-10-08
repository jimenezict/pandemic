package com.dataontheroad.pandemic.model.cards.model.special_card;

import static com.dataontheroad.pandemic.constants.LiteralsCard.SPECIAL_EVENT_GOVERNMENT_GRANT_DESCRIPTION;
import static com.dataontheroad.pandemic.constants.LiteralsCard.SPECIAL_EVENT_GOVERNMENT_GRANT_NAME;

public class GovernmentGrantEventCard extends SpecialCard {

    public GovernmentGrantEventCard() {
        super();
        this.eventName = SPECIAL_EVENT_GOVERNMENT_GRANT_NAME;
        this.eventDescription = SPECIAL_EVENT_GOVERNMENT_GRANT_DESCRIPTION;
    }

}

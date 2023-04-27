package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.DiscoverCureDefaultService;

public class ScientistPlayer extends Player {

    public ScientistPlayer() {
        super();
        color = "white";
        name = "scientist";
        description = "You need only 4 cards of the same color to do the Discover a Cure action";
        numOfCardsForDiscoveringCure = 4;
    }

}

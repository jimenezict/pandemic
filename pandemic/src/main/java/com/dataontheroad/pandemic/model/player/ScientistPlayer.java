package com.dataontheroad.pandemic.model.player;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class ScientistPlayer extends Player {

    public ScientistPlayer() {
        super();
        color = SCIENTIST_COLOR;
        name = SCIENTIST_NAME;
        description = SCIENTIST_DESCRIPTION;
        numOfCardsForDiscoveringCure = 4;
    }

}

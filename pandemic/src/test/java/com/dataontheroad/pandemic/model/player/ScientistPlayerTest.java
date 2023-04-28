package com.dataontheroad.pandemic.model.player;

import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;
import static org.junit.jupiter.api.Assertions.*;

class ScientistPlayerTest {

    @Test
    void createCientist() {
        ScientistPlayer scientistPlayer = new ScientistPlayer();
        assertEquals(4, scientistPlayer.getNumOfCardsForDiscoveringCure());
        assertEquals(SCIENTIST_NAME, scientistPlayer.getName());
        assertEquals(SCIENTIST_COLOR, scientistPlayer.getColor());
        assertEquals(SCIENTIST_DESCRIPTION, scientistPlayer.getDescription());
    }

}
package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.player_services.OperationsBuildResearchCenterService;
import com.dataontheroad.pandemic.actions.player_services.ShareKnowledgeResearchService;
import org.junit.jupiter.api.Test;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;
import static org.junit.jupiter.api.Assertions.*;

class ResearchPlayerTest {

    @Test
    void createResearchPlayerSpecialist() {
        ResearchPlayer researchPlayer = new ResearchPlayer();
        assertEquals(5, researchPlayer.getNumOfCardsForDiscoveringCure());
        assertEquals(RESEARCHER_NAME, researchPlayer.getName());
        assertEquals(RESEARCHER_COLOR, researchPlayer.getColor());
        assertEquals(RESEARCHER_DESCRIPTION, researchPlayer.getDescription());
    }

    @Test
    void getSharesKnowledgeService() {
        ResearchPlayer researchPlayer = new ResearchPlayer();
        assertInstanceOf(ShareKnowledgeResearchService.class, researchPlayer.getShareKnowledge());
    }

}
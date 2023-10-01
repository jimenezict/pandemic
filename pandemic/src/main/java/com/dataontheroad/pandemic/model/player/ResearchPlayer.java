package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.ShareKnowledgeDefaultService;
import com.dataontheroad.pandemic.actions.player_services.ShareKnowledgeResearchService;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;

public class ResearchPlayer extends Player {

    public ResearchPlayer() {
        super();
        color = RESEARCHER_COLOR;
        name = RESEARCHER_NAME;
        description = RESEARCHER_DESCRIPTION;
        shareKnowledgeDefaultService = new ShareKnowledgeResearchService();
    }

}

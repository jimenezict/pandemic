package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.*;

public interface IPlayerServices {

    BuildResearchCenterDefaultService getBuildResearchCenter();

    DiscoverCureDefaultService getDiscoverCure();

    DriveFerryDefaultService getDriveFerry();

    FlyCharterDefaultService getFlyCharter();

    FlyDirectCityDefaultService getFlyDirectCity();

    FlyShuttleDefaultService getFlyShuttle();

    ShareKnowledgeDefaultService getShareKnowledge();

    TreatDiseaseDefaultService getTreatDisease();
}

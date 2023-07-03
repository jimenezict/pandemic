package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.*;

public interface IPlayerServices {

    default BuildResearchCenterDefaultService getBuildResearchCenter() {
        return BuildResearchCenterDefaultService.getInstance();
    }

    default DiscoverCureDefaultService getDiscoverCure() {
        return DiscoverCureDefaultService.getInstance();
    }

    default DriveFerryDefaultService getDriveFerry() {
        return DriveFerryDefaultService.getInstance();
    }

    default FlyCharterDefaultService getFlyCharter() {
        return FlyCharterDefaultService.getInstance();
    }

    default FlyDirectCityDefaultService getFlyDirectCity() {
        return FlyDirectCityDefaultService.getInstance();
    }

    default FlyShuttleDefaultService getFlyShuttle() {
        return FlyShuttleDefaultService.getInstance();
    }

    default ShareKnowledgeDefaultService getShareKnowledge() {
        return ShareKnowledgeDefaultService.getInstance();
    }

    default TreatDiseaseDefaultService getTreatDisease() { return TreatDiseaseDefaultService.getInstance();}
}

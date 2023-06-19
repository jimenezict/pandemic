package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.actions.default_services.*;

public interface IPlayerServices {

    public default BuildResearchCenterDefaultService getBuildResearchCenter() {
        return BuildResearchCenterDefaultService.getInstance();
    }

    public default DiscoverCureDefaultService getDiscoverCure() {
        return DiscoverCureDefaultService.getInstance();
    }

    public default DriveFerryDefaultService getDriveFerry() {
        return DriveFerryDefaultService.getInstance();
    }

    public default FlyCharterDefaultService getFlyCharter() {
        return FlyCharterDefaultService.getInstance();
    }

    public default FlyDirectCityDefaultService getFlyDirectCity() {
        return FlyDirectCityDefaultService.getInstance();
    }

    public default FlyShuttleDefaultService getFlyShuttle() {
        return FlyShuttleDefaultService.getInstance();
    }

    public default ShareKnowledgeDefaultService getShareKnowledge() {
        return ShareKnowledgeDefaultService.getInstance();
    }

    public default TreatDiseaseDefaultService getTreatDisease() { return TreatDiseaseDefaultService.getInstance();}
}

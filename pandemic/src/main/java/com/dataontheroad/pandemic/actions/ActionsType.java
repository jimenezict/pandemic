package com.dataontheroad.pandemic.actions;

public enum ActionsType {
    BUILDRESEARCHCENTER("Build Research Center"),
    SHAREKNOWLEDGE("Share knowledge"),
    FLYBETWEENRESEARCH("Fly between research center"),
    MOVENODECITY("Move to near city when there is a direct connection"),
    FLYDIRECT("Discard a City card to move to the city named on the card"),
    FLYCHARTER("Discard the City card that matches the city you are in to move to any city");

    public final String label;
    private ActionsType(String label) {
        this.label = label;
    }
}

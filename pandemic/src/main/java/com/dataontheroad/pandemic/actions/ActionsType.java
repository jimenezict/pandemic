package com.dataontheroad.pandemic.actions;

public enum ActionsType {
    BUILDRESEARCHCENTER("Build Research Center"),
    SHAREKNOWLEDGE("Share knowledge");

    public final String label;
    private ActionsType(String label) {
        this.label = label;
    }
}

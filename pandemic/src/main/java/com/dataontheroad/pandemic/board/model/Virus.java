package com.dataontheroad.pandemic.board.model;

import com.dataontheroad.pandemic.board.model.enums.VirusType;

public class Virus {

    private final VirusType virusType;
    private Boolean cureDiscovered = Boolean.FALSE;
    private Boolean eradicated = Boolean.FALSE;


    public Virus(VirusType virusType) {
        this.virusType = virusType;
    }

    public void cureHasBeenDiscovered() {
        cureDiscovered = Boolean.TRUE;
    }

    public void virusHasBeenEradicated() {
        eradicated = Boolean.TRUE;
    }

    public VirusType getVirusType() {
        return virusType;
    }

    public Boolean getCureDiscovered() {
        return cureDiscovered;
    }

    public Boolean getEradicated() {
        return eradicated;
    }
}

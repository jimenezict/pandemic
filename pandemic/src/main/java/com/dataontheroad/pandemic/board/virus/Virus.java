package com.dataontheroad.pandemic.board.virus;

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

    public boolean getCureDiscovered() {
        return cureDiscovered;
    }

    public Boolean getEradicated() {
        return eradicated;
    }
}

package com.dataontheroad.pandemic.model.virus;

import java.util.Objects;

public class Virus {

    private final VirusType virusType;
    private boolean cureDiscovered = Boolean.FALSE;
    private boolean eradicated = Boolean.FALSE;


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

    public boolean getEradicated() {
        return eradicated;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        Virus virus = (Virus) obj;
        return virus.getVirusType().equals(virusType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(virusType, cureDiscovered, eradicated);
    }
}

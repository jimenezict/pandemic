package com.dataontheroad.pandemic.model.virus;

import com.dataontheroad.pandemic.model.cards.model.CityCard;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        Virus virus = (Virus) obj;
        return virus.getVirusType().equals(virusType);
    }
}

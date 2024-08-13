package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicPlayerTest {

    MedicPlayer mediaPlayer;
    private final City atlanta = new City("Atlanta", VirusType.BLUE);
    Virus cureVirus;
    Virus uncureVirus;
    List<Virus> virusList;

    @BeforeEach
    public void setUp() {
        mediaPlayer = new MedicPlayer();
        mediaPlayer.setCity(atlanta);
        atlanta.getVirusBoxes().clear();

        cureVirus = new Virus(VirusType.BLUE);
        cureVirus.cureHasBeenDiscovered();
        uncureVirus = new Virus(VirusType.YELLOW);
        virusList = new ArrayList<>(Arrays.asList(cureVirus, uncureVirus));
    }

    //Atlanta has 2 yellow boxes and 1 blue, yellow cure is discovered
    @Test
    void remove2yellow() {
        atlanta.addVirusBoxes(VirusType.BLUE);
        atlanta.addVirusBoxes(VirusType.BLUE);
        atlanta.addVirusBoxes(VirusType.YELLOW);

        mediaPlayer.executeAfterAction(virusList);
        assertEquals(VirusType.YELLOW, mediaPlayer.getCity().getVirusBoxes().get(0));
        assertEquals(1, mediaPlayer.getCity().getVirusBoxes().size());
    }
}
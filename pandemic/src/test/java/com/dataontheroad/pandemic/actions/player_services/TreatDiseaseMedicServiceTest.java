package com.dataontheroad.pandemic.actions.player_services;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.MedicPlayer;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TreatDiseaseMedicServiceTest {

    private MedicPlayer medic;
    private City atlanta = new City("Atlanta", VirusType.BLUE);
    private static TreatDiseaseMedicService treatDiseaseMedicService;

    @BeforeEach
    void setUp() {
        medic = new MedicPlayer();
        ArrayList<VirusType> virusList = new ArrayList<>(Arrays.asList(VirusType.BLUE, VirusType.BLUE, VirusType.BLACK));
        atlanta.setVirusBoxes(virusList);
        medic.setCity(atlanta);
    }

    @Test
    void doAction_remove2BlueVirus() throws ActionException {
        TreatDiseaseMedicService.doAction(medic, new Virus(VirusType.BLUE));
        assertEquals(1, atlanta.getVirusBoxes().size());
        assertEquals(VirusType.BLACK, atlanta.getVirusBoxes().get(0));
    }

    @Test
    void doAction_remove1BlackVirus() throws ActionException {
        TreatDiseaseMedicService.doAction(medic, new Virus(VirusType.BLACK));
        assertEquals(2, atlanta.getVirusBoxes().size());
        assertEquals(VirusType.BLUE, atlanta.getVirusBoxes().get(0));
        assertEquals(VirusType.BLUE, atlanta.getVirusBoxes().get(1));
    }
}
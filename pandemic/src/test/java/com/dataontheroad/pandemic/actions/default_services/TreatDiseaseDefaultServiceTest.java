package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreatDiseaseDefaultServiceTest {

    Player player;
    private City atlanta = new City("Atlanta", VirusType.BLUE);
    Virus cureVirus;
    Virus uncureVirus;
    List<Virus> virusList;
    TreatDiseaseDefaultService treatDiseaseDefaultService = TreatDiseaseDefaultService.getInstance();

    @BeforeEach
    public void setUp() {
        player = new Player();
        atlanta.setVirusBoxes(new ArrayList<>());
        player.setCity(atlanta);
        cureVirus = new Virus(VirusType.BLUE);
        cureVirus.cureHasBeenDiscovered();
        uncureVirus = new Virus(VirusType.BLACK);
        virusList = new ArrayList<>(Arrays.asList(cureVirus, uncureVirus));
    }

    @Test
    void isDoable_TreatDiseaseDefaultService_EmptyCity_ThenFalse() {
        assertFalse(treatDiseaseDefaultService.isDoable(player));
    }

    @Test
    void isDoable_TreatDiseaseDefaultService_BlackVirus_ThenTrue() {
        player.getCity().addVirusBoxes(uncureVirus.getVirusType());
        assertTrue(treatDiseaseDefaultService.isDoable(player));
    }

    @Test
    void isDoable_TreatDiseaseDefaultService_BlackBlueBlueVirus_ThenTrue() {
        player.getCity().addVirusBoxes(uncureVirus.getVirusType());
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        assertTrue(treatDiseaseDefaultService.isDoable(player));
    }

    @Test
    void returnAvailableActions_TreatDiseaseDefaultService_Empty() {
        assertEquals(0, treatDiseaseDefaultService.returnAvailableActions(player, virusList).size());
    }

    @Test
    void returnAvailableActions_TreatDiseaseDefaultService_Blue() {
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        assertEquals(1, treatDiseaseDefaultService.returnAvailableActions(player, virusList).size());
    }

    @Test
    void returnAvailableActions_TreatDiseaseDefaultService_BlueBlue() {
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        assertEquals(1, treatDiseaseDefaultService.returnAvailableActions(player, virusList).size());
    }

    @Test
    void returnAvailableActions_TreatDiseaseDefaultService_BlueBlueBlack() {
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(uncureVirus.getVirusType());
        assertEquals(2, treatDiseaseDefaultService.returnAvailableActions(player, virusList).size());
    }

    @Test
    void doAction_TreatDiseaseDefaultService_doAction_SuccessWhenHasOneBlue() throws ActionException {
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        treatDiseaseDefaultService.doAction(player, cureVirus);
        assertEquals(0, player.getCity().getVirusBoxes().size());
    }

    @Test
    void doAction_TreatDiseaseDefaultService_SuccessWhenHasTwoBlue() throws ActionException {
        // As the virus is researched, then removes all the virus boxes
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        treatDiseaseDefaultService.doAction(player, cureVirus);
        assertEquals(0, player.getCity().getVirusBoxes().size());
    }

    @Test
    void doAction_TreatDiseaseDefaultService_SuccessWhenHasTwoBlueOneBlack() throws ActionException {
        // As the virus is researched, then removes all the virus boxes
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(uncureVirus.getVirusType());
        treatDiseaseDefaultService.doAction(player, cureVirus);
        assertEquals(1, player.getCity().getVirusBoxes().size());
    }

    @Test
    void doAction_TreatDiseaseDefaultService_SuccessWhenHasTwoBlueOneBlackRemovesBlack() throws ActionException {
        // As the virus is not researched, then removes one virus boxes
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(cureVirus.getVirusType());
        player.getCity().addVirusBoxes(uncureVirus.getVirusType());
        treatDiseaseDefaultService.doAction(player, uncureVirus);
        assertEquals(2, player.getCity().getVirusBoxes().size());
    }

    @Test
    void doAction_TreatDiseaseDefaultService_doAction_SuccessWhenHasOneBlueTwoBlackRemovesBlack() throws ActionException {
        // As the virus is not researched, then removes one virus boxes
        player.getCity().addVirusBoxes(VirusType.BLUE);
        player.getCity().addVirusBoxes(VirusType.BLACK);
        player.getCity().addVirusBoxes(VirusType.BLACK);
        treatDiseaseDefaultService.doAction(player, uncureVirus);
        assertEquals(2, player.getCity().getVirusBoxes().size());
    }

}
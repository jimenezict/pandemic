package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.model.city.CityFactory.createCityList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EndOfGameServiceImplTest {

    @InjectMocks
    EndOfGameServiceImpl endOfGameServiceImpl;

    private List<Virus> virusList;
    private List<City> cityList;

    private final Virus blueVirus = new Virus(VirusType.BLUE);
    private final Virus blackVirus = new Virus(VirusType.BLACK);
    private final Virus redVirus = new Virus(VirusType.RED);
    private final Virus yellowVirus = new Virus(VirusType.YELLOW);

    @BeforeEach
    public void setUp() {
        virusList = Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
        cityList = createCityList();
    }
    @Test
    void allVirusHadBeenEradicated_anyEradicated() {
        assertFalse(endOfGameServiceImpl.allVirusHadBeenEradicated(virusList));
    }

    @Test
    void allVirusHadBeenEradicated_someEradicated() {
        virusList.get(0).cureHasBeenDiscovered();
        virusList.get(0).virusHasBeenEradicated();
        assertFalse(endOfGameServiceImpl.allVirusHadBeenEradicated(virusList));
    }

    @Test
    void allVirusHadBeenEradicated_allEradicated() {
        for(int i = 0; i < virusList.size(); i++) {
            virusList.get(i).cureHasBeenDiscovered();
            virusList.get(i).virusHasBeenEradicated();
        }
        assertTrue(endOfGameServiceImpl.allVirusHadBeenEradicated(virusList));
    }

    @Test
    void returnVirusIfOverPassTheMaximalNumberOrNull_allCitiesEmpty() {
        assertNull(endOfGameServiceImpl.returnVirusIfOverPassTheMaximalNumberOrNull(cityList));
    }

    @Test
    void returnVirusIfOverPassTheMaximalNumberOrNull_SomeYellowBox() {
        for(int i=0; i< 5; i ++) {
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
        }
        assertNull(endOfGameServiceImpl.returnVirusIfOverPassTheMaximalNumberOrNull(cityList));
    }

    @Test
    void returnVirusIfOverPassTheMaximalNumberOrNull_CityYellowBox() {
        for(int i=0; i< 9; i ++) {
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
        }
        assertEquals(VirusType.YELLOW, endOfGameServiceImpl.returnVirusIfOverPassTheMaximalNumberOrNull(cityList));
    }

    @Test
    void allCitiesWithoutBoxes_noBoxesOnTheBoard() {
        assertTrue(endOfGameServiceImpl.allCitiesWithoutBoxes(cityList));
    }

    @Test
    void allCitiesWithoutBoxes_BoxesOnTheBoard() {
        for(int i=0; i< 5; i ++) {
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
            cityList.get(i).addVirusBoxes(VirusType.YELLOW);
        }

        assertFalse(endOfGameServiceImpl.allCitiesWithoutBoxes(cityList));
    }
}
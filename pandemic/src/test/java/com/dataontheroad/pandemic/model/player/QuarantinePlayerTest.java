package com.dataontheroad.pandemic.model.player;

import com.dataontheroad.pandemic.model.city.City;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsPlayers.*;
import static com.dataontheroad.pandemic.model.city.CityEnum.*;
import static com.dataontheroad.pandemic.model.city.CityFactory.createCityList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuarantinePlayerTest {

    @Test
    void createQuarantineSpecialist() {
        QuarantinePlayer quarantinePlayer = new QuarantinePlayer();
        assertEquals(5, quarantinePlayer.getNumOfCardsForDiscoveringCure());
        assertEquals(QUARANTINE_NAME, quarantinePlayer.getName());
        assertEquals(QUARANTINE_COLOR, quarantinePlayer.getColor());
        assertEquals(QUARANTINE_DESCRIPTION, quarantinePlayer.getDescription());
    }

    @Test
    void validateCitiesWhereCanNotPropagate() {
        List<City> cityList = createCityList();
        QuarantinePlayer quarantinePlayer = new QuarantinePlayer();
        quarantinePlayer.setCity(cityList.get(1));

        List<City> citiesToValidate = quarantinePlayer.getCitiesWherePreventsToPropagate();
        assertTrue(citiesToValidate.contains(new City(ATLANTA.cityName, ATLANTA.virusType)));
        assertTrue(citiesToValidate.contains(new City(CHICAGO.cityName, CHICAGO.virusType)));
        assertTrue(citiesToValidate.contains(new City(MIAMI.cityName, MIAMI.virusType)));
        assertTrue(citiesToValidate.contains(new City(WASHINGTON.cityName, WASHINGTON.virusType)));
    }

}
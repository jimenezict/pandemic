package com.dataontheroad.pandemic.model.city;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dataontheroad.pandemic.model.city.CityFactory.createCityList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CityFactoryTest {

    @Test
    void createCityListFullTest() {
        List<City> cityList = createCityList();
        City atlanta = cityList.stream().filter(city -> city.getName().equals("Atlanta")).findFirst().orElse(null);

        assertEquals(48, cityList.size());
        assertTrue(atlanta.getHasCenter());
        assertEquals(3, sizeConnectionByCityName("Atlanta", cityList));
        assertEquals(5, sizeConnectionByCityName("Chicago", cityList));
        assertEquals(3, sizeConnectionByCityName("Montreal", cityList));
        assertEquals(4, sizeConnectionByCityName("Washington", cityList));
        assertEquals(4, sizeConnectionByCityName("San Francisco", cityList));
        assertEquals(4, sizeConnectionByCityName("Los Angeles", cityList));
        assertEquals(5, sizeConnectionByCityName("Mexico City", cityList));
        assertEquals(4, sizeConnectionByCityName("Miami", cityList));
        assertEquals(4, sizeConnectionByCityName("New York", cityList));
    }

    private int sizeConnectionByCityName(String name, List<City> cityList) {
        City city = cityList.stream().filter(cityStream -> cityStream.getName().equals(name)).findFirst().orElse(null);
        return city.getNodeCityConnection().size();
    }


}
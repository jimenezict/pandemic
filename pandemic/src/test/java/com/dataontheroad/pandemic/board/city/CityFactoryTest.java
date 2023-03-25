package com.dataontheroad.pandemic.board.city;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dataontheroad.pandemic.board.city.CityFactory.*;
import static org.junit.jupiter.api.Assertions.*;

class CityFactoryTest {

    @Test
    public void createCityListWithoutConnectionsTest() {
        List<City> cityList = createCityListWithoutConnections();
        assertEquals(48, cityList.size());
    }

    @Test
    public void atlantaHasResearchCenterTest() {
        List<City> cityList = createCityListWithoutConnections();
        atlantaHasResearchCenter(cityList);
        City atlanta = cityList.stream().filter(city -> city.getName().equals("Atlanta")).findFirst().orElse(null);
        assertEquals(48, cityList.size());
        assertTrue(atlanta.getHasCenter());
    }

    @Test
    public void createConnection() {
        List<City> cityList = createCityListWithoutConnections();
        addConnectionCityList(cityList);
        assertEquals(48, cityList.size());

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
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

        assertEquals(4, sizeConnectionByCityName("Algiers", cityList));
        assertEquals(3, sizeConnectionByCityName("Atlanta", cityList));
        assertEquals(5, sizeConnectionByCityName("Baghdad", cityList));
        assertEquals(2, sizeConnectionByCityName("Beijing", cityList));
        assertEquals(5, sizeConnectionByCityName("Bogota", cityList));
        assertEquals(2, sizeConnectionByCityName("Buenos Aires", cityList));
        assertEquals(5, sizeConnectionByCityName("Cairo", cityList));
        assertEquals(5, sizeConnectionByCityName("Chennai", cityList));
        assertEquals(5, sizeConnectionByCityName("Chicago", cityList));
        assertEquals(5, sizeConnectionByCityName("Delhi", cityList));
        assertEquals(4, sizeConnectionByCityName("Essen", cityList));
        assertEquals(4, sizeConnectionByCityName("Ho Chi Minh City", cityList));
        assertEquals(6, sizeConnectionByCityName("Hong Kong", cityList));
        assertEquals(6, sizeConnectionByCityName("Istanbul", cityList));
        assertEquals(4, sizeConnectionByCityName("Jakarta", cityList));
        assertEquals(2, sizeConnectionByCityName("Johannesburg", cityList));
        assertEquals(5, sizeConnectionByCityName("Karachi", cityList));
        assertEquals(4, sizeConnectionByCityName("Khartoum", cityList));
        assertEquals(3, sizeConnectionByCityName("Kinshasa", cityList));
        assertEquals(4, sizeConnectionByCityName("Kolkata", cityList));
        assertEquals(3, sizeConnectionByCityName("Lagos", cityList));
        assertEquals(3, sizeConnectionByCityName("Lima", cityList));
        assertEquals(4, sizeConnectionByCityName("London", cityList));
        assertEquals(4, sizeConnectionByCityName("Los Angeles", cityList));
        assertEquals(5, sizeConnectionByCityName("Madrid", cityList));
        assertEquals(5, sizeConnectionByCityName("Manila", cityList));
        assertEquals(5, sizeConnectionByCityName("Mexico City", cityList));
        assertEquals(4, sizeConnectionByCityName("Miami", cityList));
        assertEquals(3, sizeConnectionByCityName("Milan", cityList));
        assertEquals(3, sizeConnectionByCityName("Montreal", cityList));
        assertEquals(3, sizeConnectionByCityName("Moscow", cityList));
        assertEquals(3, sizeConnectionByCityName("Mumbai", cityList));
        assertEquals(4, sizeConnectionByCityName("New York", cityList));
        assertEquals(2, sizeConnectionByCityName("Osaka", cityList));
        assertEquals(5, sizeConnectionByCityName("Paris", cityList));
        assertEquals(3, sizeConnectionByCityName("Riyadh", cityList));
        assertEquals(4, sizeConnectionByCityName("San Francisco", cityList));
        assertEquals(1, sizeConnectionByCityName("Santiago", cityList));
        assertEquals(4, sizeConnectionByCityName("Sao Paulo", cityList));
        assertEquals(3, sizeConnectionByCityName("Seoul", cityList));
        assertEquals(5, sizeConnectionByCityName("Shanghai", cityList));
        assertEquals(3, sizeConnectionByCityName("St. Petersburg", cityList));
        assertEquals(3, sizeConnectionByCityName("Sydney", cityList));
        assertEquals(4, sizeConnectionByCityName("Taipei", cityList));
        assertEquals(4, sizeConnectionByCityName("Tehran", cityList));
        assertEquals(4, sizeConnectionByCityName("Tokyo", cityList));
        assertEquals(4, sizeConnectionByCityName("Washington", cityList));
    }

    private int sizeConnectionByCityName(String name, List<City> cityList) {
        City city = cityList.stream().filter(cityStream -> cityStream.getName().equals(name)).findFirst().orElse(null);
        return city.getNodeCityConnection().size();
    }


}
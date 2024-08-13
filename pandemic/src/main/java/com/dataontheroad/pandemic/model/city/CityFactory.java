package com.dataontheroad.pandemic.model.city;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class CityFactory {

    private CityFactory()  {
        throw new IllegalStateException("Utility class");
    }

    //
    public static List<City> createCityList() {
        List<City> cityList = createCityListWithoutConnections();
        addConnectionCityList(cityList);
        atlantaHasResearchCenter(cityList);
        return cityList;
    }

    private static List<City> createCityListWithoutConnections() {
        return Arrays.stream(CityEnum.values())
                .map(cityEnum -> new City(cityEnum.cityName, cityEnum.virusType))
                .collect(Collectors.toList());
    }

    private static List<City> addConnectionCityList(List<City> cityList) {
        List<CityConnectionEnum> citiesConnectionEnum = Arrays.asList(CityConnectionEnum.values());
        citiesConnectionEnum.forEach(cityConnectionEnum -> {
            City city1 = cityList.stream().filter(city -> city.getName().equals(cityConnectionEnum.city1)).findFirst().orElse(null);
            City city2 = cityList.stream().filter(city -> city.getName().equals(cityConnectionEnum.city2)).findFirst().orElse(null);

            city1.getNodeCityConnection().add(city2);
            city2.getNodeCityConnection().add(city1);
        });

        return cityList;
    }

    private static List<City> atlantaHasResearchCenter(List<City> cityList) {
        City atlanta = cityList.stream().filter(city -> city.getName().equals("Atlanta")).findFirst().orElse(null);
        if(!isNull(atlanta)) atlanta.setHasCenter(Boolean.TRUE);
        return cityList;
    }

}

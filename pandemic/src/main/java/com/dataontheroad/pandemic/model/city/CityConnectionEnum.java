package com.dataontheroad.pandemic.model.city;

import static com.dataontheroad.pandemic.model.city.CityEnum.*;

public enum CityConnectionEnum {
    CON1(ATLANTA.cityName, CHICAGO.cityName),
    CON2(ATLANTA.cityName, WASHINGTON.cityName),
    CON3(ATLANTA.cityName, MIAMI.cityName),
    CON4(MONTREAL.cityName, NEWYORK.cityName),
    CON5(MONTREAL.cityName, WASHINGTON.cityName),
    CON6(MONTREAL.cityName, CHICAGO.cityName),
    CON7(WASHINGTON.cityName, MIAMI.cityName),
    CON8(WASHINGTON.cityName, NEWYORK.cityName),
    CON9(CHICAGO.cityName, SANFRANCISCO.cityName),
    CON10(CHICAGO.cityName, LOSANGELES.cityName),
    CON11(CHICAGO.cityName, MEXICOCITY.cityName),
    CON12(SANFRANCISCO.cityName, LOSANGELES.cityName),
    CON13(MEXICOCITY.cityName, LOSANGELES.cityName),
    CON14(SANFRANCISCO.cityName, TOKYO.cityName),
    CON15(SANFRANCISCO.cityName, MANILA.cityName),
    CON16(LOSANGELES.cityName, SYDNEY.cityName),
    CON17(MEXICOCITY.cityName, MIAMI.cityName),
    CON18(MEXICOCITY.cityName, BOGOTA.cityName),
    CON19(MEXICOCITY.cityName, LIMA.cityName),
    CON20(MIAMI.cityName, BOGOTA.cityName),
    CON21(NEWYORK.cityName, LONDON.cityName),
    CON22(NEWYORK.cityName, MADRID.cityName),
    CON23(BOGOTA.cityName, LIMA.cityName),
    CON24(BOGOTA.cityName, BUENOSARIES.cityName),
    CON25(BOGOTA.cityName, SAOPAULO.cityName),
    CON26(LIMA.cityName, SANTIAGO.cityName),
    CON28(BUENOSARIES.cityName, SAOPAULO.cityName),
    CON29(SAOPAULO.cityName, MADRID.cityName),
    CON30(SAOPAULO.cityName, LAGOS.cityName),
    CON31(LAGOS.cityName, KHARTOUM.cityName),
    CON32(LAGOS.cityName, KINSHASA.cityName),
    CON33(KINSHASA.cityName, KHARTOUM.cityName),
    CON34(JOHANNESBURG.cityName, KHARTOUM.cityName),
    CON35(KHARTOUM.cityName, CAIRO.cityName),
    CON36(KINSHASA.cityName, JOHANNESBURG.cityName),

    ;
    String city1;
    String city2;

    CityConnectionEnum(String city1, String city2) {
        this.city1 = city1;
        this.city2 = city2;
    }
}

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
    CON37(ALGIERS.cityName, MADRID.cityName),
    CON38(ALGIERS.cityName, PARIS.cityName),
    CON39(ALGIERS.cityName, ISTANBUL.cityName),
    CON40(ALGIERS.cityName, CAIRO.cityName),
    CON41(BAGHDAD.cityName, CAIRO.cityName),
    CON42(BAGHDAD.cityName, ISTANBUL.cityName),
    CON43(BAGHDAD.cityName, KARACHI.cityName),
    CON44(BAGHDAD.cityName, TEHRAN.cityName),
    CON45(BAGHDAD.cityName, RIYADH.cityName),
    CON46(BEIJING.cityName, SEOUL.cityName),
    CON47(BEIJING.cityName, SHANGHAI.cityName),
    CON48(CAIRO.cityName, ISTANBUL.cityName),
    CON49(CAIRO.cityName, RIYADH.cityName),
    CON51(CHENNAI.cityName, MUMBAI.cityName),
    CON52(CHENNAI.cityName, DELHI.cityName),
    CON53(CHENNAI.cityName, KOLKATA.cityName),
    CON54(CHENNAI.cityName, BANGKOK.cityName),
    CON55(CHENNAI.cityName, JAKARTA.cityName),
    CON56(DELHI.cityName, TEHRAN.cityName),
    CON57(DELHI.cityName, KARACHI.cityName),
    CON58(DELHI.cityName, MUMBAI.cityName),
    CON59(DELHI.cityName, KOLKATA.cityName),
    CON60(ESSEN.cityName, LONDON.cityName),
    CON61(ESSEN.cityName, PARIS.cityName),
    CON62(ESSEN.cityName, MILAN.cityName),
    CON63(ESSEN.cityName, STPETERSBURG.cityName),
    CON64(HOCHIMINHCITY.cityName, BANGKOK.cityName),
    CON65(HOCHIMINHCITY.cityName, JAKARTA.cityName),
    CON66(HOCHIMINHCITY.cityName, MANILA.cityName),
    CON67(HOCHIMINHCITY.cityName, HONGKONG.cityName),
    CON68(HONGKONG.cityName, SHANGHAI.cityName),
    CON69(HONGKONG.cityName, TAIPEI.cityName),
    CON70(HONGKONG.cityName, MANILA.cityName),
    CON71(HONGKONG.cityName, BANGKOK.cityName),
    CON72(HONGKONG.cityName, KOLKATA.cityName),
    CON73(ISTANBUL.cityName, MILAN.cityName),
    CON74(ISTANBUL.cityName, STPETERSBURG.cityName),
    CON75(ISTANBUL.cityName, MOSCOW.cityName),
    CON76(JAKARTA.cityName, SYDNEY.cityName),
    CON77(JAKARTA.cityName, BANGKOK.cityName),
    CON78(KARACHI.cityName, TEHRAN.cityName),
    CON79(KARACHI.cityName, RIYADH.cityName),
    CON80(KARACHI.cityName, MUMBAI.cityName),
    CON81(KOLKATA.cityName, BANGKOK.cityName),
    CON82(LONDON.cityName, PARIS.cityName),
    CON83(LONDON.cityName, MADRID.cityName),
    CON84(PARIS.cityName, MADRID.cityName),
    CON85(MANILA.cityName, TAIPEI.cityName),
    CON86(MANILA.cityName, SYDNEY.cityName),
    CON87(MILAN.cityName, PARIS.cityName),
    CON88(MOSCOW.cityName, STPETERSBURG.cityName),
    CON89(MOSCOW.cityName, TEHRAN.cityName),
    CON90(OSAKA.cityName, TOKYO.cityName),
    CON91(OSAKA.cityName, TAIPEI.cityName),
    CON92(SEOUL.cityName, SHANGHAI.cityName),
    CON93(SEOUL.cityName, TOKYO.cityName),
    CON94(SHANGHAI.cityName, TOKYO.cityName),
    CON95(SHANGHAI.cityName, TAIPEI.cityName),


    ;
    final String city1;
    final String city2;

    CityConnectionEnum(String city1, String city2) {
        this.city1 = city1;
        this.city2 = city2;
    }
}

package com.dataontheroad.pandemic.board.city;

public enum CityConnectionEnum {
    CON1("Atlanta", "Chicago"),
    CON2("Atlanta", "Washington"),
    CON3("Atlanta", "Miami"),
    CON4("Montreal", "New York"),
    CON5("Montreal", "Washington"),
    CON6("Montreal", "Chicago"),
    CON7("Washington", "Miami"),
    CON8("Washington", "New York"),
    CON9("Chicago", "San Francisco"),
    CON10("Chicago", "Los Angeles"),
    CON11("Chicago", "Mexico City"),
    CON12("San Francisco", "Los Angeles"),
    CON13("Mexico City", "Los Angeles"),
    CON14("San Francisco", "Tokyo"),
    CON15("San Francisco", "Manila"),
    CON16("Los Angeles", "Sydney"),
    CON17("Mexico City", "Miami"),
    CON18("Mexico City", "Bogota"),
    CON19("Mexico City", "Lima"),
    CON20("Miami", "Bogota"),
    CON21("New York", "London"),
    CON22("New York", "Madrid"),
    ;
    String city1, city2;

    CityConnectionEnum(String city1, String city2) {
        this.city1 = city1;
        this.city2 = city2;
    }
}

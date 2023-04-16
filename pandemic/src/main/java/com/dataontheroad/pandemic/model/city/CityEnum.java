package com.dataontheroad.pandemic.model.city;

import com.dataontheroad.pandemic.model.virus.VirusType;

public enum CityEnum {
    ALGIERS("Algiers",VirusType.BLACK),
    ATLANTA("Atlanta",VirusType.BLUE),
    BAGHDAD("Baghdad",VirusType.RED),
    BANGKOK("Bangkok",VirusType.RED),
    BEIJING("Beijing",VirusType.RED),
    BOGOTA("Bogota",VirusType.YELLOW),
    BUENOSARIES("Buenos Aries",VirusType.YELLOW),
    CAIRO("Cairo",VirusType.BLACK),
    CHENNAI("Chennai",VirusType.BLACK),
    CHICAGO("Chicago",VirusType.BLUE),
    DELHI("Delhi",VirusType.BLACK),
    ESSEN("Essen",VirusType.BLUE),
    HOCHIMINHCITY("Ho Chi Minh City",VirusType.RED),
    HONGKONG("Hong Kong",VirusType.RED),
    ISTANBUL("Istanbul",VirusType.BLACK),
    JAKARTA("Jakarta",VirusType.RED),
    JOHANNESBURG("Johannesburg",VirusType.YELLOW),
    KARACHI("Karachi",VirusType.BLACK),
    KHARTOUM("Khartoum",VirusType.YELLOW),
    KINSHASA("Kinshasa",VirusType.YELLOW),
    KOLKATA("Kolkata",VirusType.BLACK),
    LAGOS("Lagos",VirusType.YELLOW),
    LIMA("Lima",VirusType.YELLOW),
    LONDON("London",VirusType.BLUE),
    LOSANGELES("Los Angeles",VirusType.BLUE),
    MADRID("Madrid",VirusType.BLUE),
    MANILA("Manila",VirusType.RED),
    MEXICOCITY("Mexico City",VirusType.YELLOW),
    MIAMI("Miami",VirusType.YELLOW),
    MILAN("Milan",VirusType.BLUE),
    MONTREAL("Montreal",VirusType.BLUE),
    MOSCOW("Moscow",VirusType.BLACK),
    MUMBAI("Mumbai",VirusType.BLACK),
    NEWYORK("New York",VirusType.BLUE),
    OSAKA("Osaka",VirusType.RED),
    PARIS("Paris",VirusType.BLUE),
    RIYADH("Riyadh",VirusType.BLACK),
    SANFRANCISCO("San Francisco",VirusType.BLUE),
    SANTIAGO("Santiago",VirusType.YELLOW),
    SAOPAULO("Sao Paulo",VirusType.YELLOW),
    SEOUL("Seoul",VirusType.RED),
    SHANGHAI("Shanghai",VirusType.RED),
    STPETERSBURG("St. Petersburg",VirusType.BLUE),
    SYDNEY("Sydney",VirusType.RED),
    TAIPEI("Taipei",VirusType.RED),
    TEHRAN("Tehran",VirusType.BLACK),
    TOKYO("Tokyo",VirusType.RED),
    WASHINGTON("Washington",VirusType.BLUE);

    public final String cityName;
    public final VirusType virusType;
    CityEnum(String cityName, VirusType virusType) {
        this.cityName = cityName;
        this.virusType = virusType;
    }
}

package com.dataontheroad.pandemic.constants;

public class Literals {

    //Build Research Station
    public static final String BUILDRESEARCHSTATION_DESCRIPTION = "Discard the City card that matches the city you are in to place a research station there";
    public static final String BUILDRESEARCHSTATION_ACTION = "Build Research Center at city: ";
    public static final String BUILDRESEARCHSTATION_ERROR_CENTER_CREATED = "Center already created";
    public static final String BUILDRESEARCHSTATION_ERROR_NO_CARD = "Player has no card for that city";
    //Share knowledge
    public static final String SHAREKNOWLEDGE_DESCRIPTION = "Give the card that matches the city you are in to another player, or take that card from another player. The other player must also be in the city with you";
    public static final String SHAREKNOWLEDGE_ERROR_NO_CARD = "Sender do not have the Card";
    public static final String SHAREKNOWLEDGE_ERROR_OVERCAPACITY = "Receiver has overpass 7 cards hand capacity";
    public static final String SHAREKNOWLEDGE_ERROR_NOT_SAME_CITY = "Sender and receiver are not on same city";
    public static final String SHAREKNOWLEDGE_ERROR_NOT_CARD_CITY = "Sender is not on the Card city";

    //Flight Shuttle
    public static final String SHUTTLEFLIGHT_DESCRIPTION = "Move from a city with a research station to any other city that has a research station";
    public static final String SHUTTLEFLIGHT_ACTION = "You can fly from Research to Research Center: ";
    public static final String SHUTTLEFLIGHT_ERROR_ORIGIN_NO_RESEARCH_STATION = "Origin city has not research center";
    public static final String SHUTTLEFLIGHT_ERROR_DESTINY_NO_RESEARCH_STATION = "Destiny city has not research center";
    //Drive ferry
    public static final String DRIVEFERRY_DESCRIPTION = "Move to a city connected by a white line";
    public static final String DRIVEFERRY_ERROR_NO_CONNECTION = "Destinition is not available for the origin city";
    //Fly direct
    public static final String FLYDIRECT_DESCRIPTION = "Discard a City card to move to the city named on the card";
    public static final String FLYDIRECT_ACTION = "You can directly fly to: ";
    public static final String FLYDIRECT_ERROR_NO_CARD = "Destination is not available for the player cards";
    //Fly charter
    public static final String FLYCHARTER_DESCRIPTION = "Discard the City card that matches the city you are in to move to any city";
    public static final String FLYCHARTER_ACTION = "You own cities card, where do you want to fly?";
    public static final String FLYCHARTER_ERROR_NO_CARD = "Player has no card to fly to the city";

    //Discover Cure
    public static final String DISCOVERCURE_DESCRIPTION = "At any research station, discard d city cards of the same disease color to cure that disease";
    public static final String DISCOVERCURE_ACTION = "Discover a Cure for: ";
    public static final String DISCOVERCURE_ERROR_NO_RESEARCH_STATION = "The city do not have a research station";
    public static final String DISCOVERCURE_ERROR_CURE_DISCOVERED = "The city do not have a research station";
    public static final String DISCOVERCURE_ERROR_NO_CARD = "The player do not have enough cards";
}

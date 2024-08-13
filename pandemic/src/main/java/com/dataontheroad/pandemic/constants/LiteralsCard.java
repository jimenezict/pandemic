package com.dataontheroad.pandemic.constants;

public class LiteralsCard {

    private LiteralsCard() {
        throw new IllegalStateException("Constant Class");
    }

    public static final String INCREASE_TEXT = "Move the Infection Rate Indicator up by one on the Infection Rate Track on the board";
    public static final String INFECT_TEXT = "Take the bottom card from the Infection Draw Pile and add 3 cubes to the city pictured on the card, then place the card into the Infection Discard Pile. Note: No city can contain more than 3 cubes of any one color. If the Epidemic would cause the city to exceed that limit, any excess cubes are returned to the stock and an outbreak is triggered.";
    public static final String INTENSIFY_TEXT = "Take the Infection Discard Pile, thoroughly shuffle it, then place it on top of the remaining Infection Draw Pile";


    public static final String SPECIAL_EVENT_GOVERNMENT_GRANT_NAME = "Government Grant";
    public static final String SPECIAL_EVENT_GOVERNMENT_GRANT_DESCRIPTION = "Add 1 research station to any city (No city card needed)";

}

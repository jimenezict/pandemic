package com.dataontheroad.pandemic.board.cards;

import com.dataontheroad.pandemic.board.cards.model.BaseCard;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.cards.model.EpidemicCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dataontheroad.pandemic.board.cards.CardTypeEnum.CITY;
import static com.dataontheroad.pandemic.board.cards.CardTypeEnum.EPIDEMIC;
import static com.dataontheroad.pandemic.board.cards.DeckCardFactory.*;
import static com.dataontheroad.pandemic.constants.LiteralsCard.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DeckCardFactoryTest {

    @Test
    void epidemicCardCreation() {
        EpidemicCard epidemicCard= new EpidemicCard();
        assertEquals(CardTypeEnum.EPIDEMIC, epidemicCard.getCardType());
        assertEquals(INCREASE_TEXT, epidemicCard.getIncreaseText());
        assertEquals(INFECT_TEXT, epidemicCard.getInfectText());
        assertEquals(INTENSIFY_TEXT, epidemicCard.getIntensifyText());
    }

    @Test
    void createEpidemicCards_shouldReturnArrayOfThree() {
        List<EpidemicCard> epidemicCards = createEpidemicCards(3);
        assertEquals(3, epidemicCards.size());
        assertEquals(3,epidemicCards.stream().filter(x -> CardTypeEnum.EPIDEMIC.equals(x.getCardType())).count());
    }

    @Test
    void createInfectionDeck_shouldCreateOnlyInfectionCards() {
        List<CityCard> cityCardsList = createInfectionDeck();
        assertEquals(48, cityCardsList.stream().count());
        assertFalse("Atlanta".equals(cityCardsList.get(0).getCity().getName()) && "".equals(cityCardsList.get(47).getCity().getName()));
    }

    @Test
    void createCityDeck_shouldCreateOnlyInfectionCards() {
        List<BaseCard> cityCardsList = createCityDeck(3);
        assertEquals(3, cityCardsList.stream().filter(card -> {
            return card.getCardType().equals(EPIDEMIC);
        }).count());
        assertEquals(48, cityCardsList.stream().filter(card -> {
            return card.getCardType().equals(CITY);
        }).count());

    }
}
package com.dataontheroad.pandemic.actions.action_factory.player_actions;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.OperationsPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.constants.LiteralsAction.OPERATIONFLY_ACTION;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlyFromResearchCenterAnywhereActionTest {

    OperationsPlayer operationsPlayer;

    private City newyork = new City("New York", VirusType.BLUE);
    private City calcuta = new City("Calcuta", VirusType.BLACK);
    private City essen = new City("Essen", VirusType.BLUE);
    private List<BaseCard> operationPlayerListCard;

    @BeforeEach
    public void setUp() {
        operationsPlayer = new OperationsPlayer();
        newyork.setHasCenter(TRUE);
        operationsPlayer.setCity(newyork);
        operationPlayerListCard = new ArrayList<>();
        operationPlayerListCard.add(createCityCard(essen));
        operationsPlayer.setListCard(operationPlayerListCard);
    }

    @Test
    void configureAction() throws ActionException {
        FlyFromResearchCenterAnywhereAction flyFromResearchCenter = new FlyFromResearchCenterAnywhereAction(operationsPlayer);
        flyFromResearchCenter.setDestination(calcuta);
        flyFromResearchCenter.setDiscardCard(operationPlayerListCard.get(0));

        assertEquals(OPERATIONFLY_ACTION, flyFromResearchCenter.actionPrompt());

        flyFromResearchCenter.execute();

        assertTrue(operationsPlayer.getListCard().isEmpty());
    }



}
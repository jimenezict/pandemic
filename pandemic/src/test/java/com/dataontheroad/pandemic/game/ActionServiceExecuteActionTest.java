package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.actionFactory.BuildResearchCenterAction;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.model.Virus;
import com.dataontheroad.pandemic.board.model.enums.VirusType;
import com.dataontheroad.pandemic.board.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.board.cards.model.CityCard.createCityCard;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class ActionServiceExecuteActionTest {

    Player player;
    List<City> emptyNodeCityConnection = new ArrayList<>();
    private City newyork = new City("New York", VirusType.BLUE, emptyNodeCityConnection);
    private City calcuta = new City("Calcuta", VirusType.BLACK, emptyNodeCityConnection);
    private City essen = new City("Essen", VirusType.BLUE, emptyNodeCityConnection);
    private City madrid = new City("Madrid", VirusType.BLUE, emptyNodeCityConnection);
    private City paris = new City("Paris", VirusType.BLUE, Arrays.asList(essen, madrid));
    private City tokio = new City("Tokio", VirusType.RED, emptyNodeCityConnection);
    private City atlanta = new City("Atlanta", VirusType.BLUE, emptyNodeCityConnection);

    private Virus blueVirus = new Virus(VirusType.BLUE);
    private Virus blackVirus = new Virus(VirusType.BLACK);
    private Virus redVirus = new Virus(VirusType.RED);
    private Virus yellowVirus = new Virus(VirusType.YELLOW);
    List<Virus> virusList;


    @BeforeEach
    public void setUp() {
        player = new Player();
        player.setCity(newyork);
        player.getListCard().add(createCityCard(newyork));
        virusList = Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
        calcuta.setHasCenter(Boolean.FALSE);
        newyork.setHasCenter(Boolean.FALSE);
        essen.setHasCenter(Boolean.FALSE);
    }

    @Test
    public void executeAction_buildResearchCenter_correct() {
        //player is in New York
        //New York do NOT have research center
        //Player has DefaultService for BuildResearchCenter
        //Player has the card of New York
        BuildResearchCenterAction action = new BuildResearchCenterAction(player);
        assertTrue(ActionService.executeAction(action));
    }

    @Test
    public void executeAction_buildResearchCenter_withoutCard() {
        //player is in New York
        //New York has NOT research center
        //Player has DefaultService for BuildResearchCenter
        //Player has not the card of New York
        player.getListCard().remove(0);
        BuildResearchCenterAction action = new BuildResearchCenterAction(player);
        assertFalse(ActionService.executeAction(action));
    }

    @Test
    public void executeAction_buildResearchCenter_hasResearchCenter() {
        //player is in New York
        //New York has a research center
        //Player has DefaultService for BuildResearchCenter
        //Player has the card of New York
        newyork.setHasCenter(TRUE);
        BuildResearchCenterAction action = new BuildResearchCenterAction(player);
        assertFalse(ActionService.executeAction(action));
    }


}
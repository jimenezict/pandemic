package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.board.cards.model.CityCard;
import com.dataontheroad.pandemic.board.city.City;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.board.virus.Virus;
import com.dataontheroad.pandemic.board.virus.VirusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dataontheroad.pandemic.actions.ActionsType.DRIVEFERRY;
import static com.dataontheroad.pandemic.board.cards.model.CityCard.createCityCard;
import static com.dataontheroad.pandemic.board.city.CityFactory.createCityList;
import static com.dataontheroad.pandemic.game.ActionService.getListOfActions;
import static com.dataontheroad.pandemic.game.ActionService.printListOfActions;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetListOfActionsActionTest {

    List<City> cityList, citiesWithResearchCenter;
    Player player;
    List<Virus> virusList;


    private Virus blueVirus = new Virus(VirusType.BLUE);
    private Virus blackVirus = new Virus(VirusType.BLACK);
    private Virus redVirus = new Virus(VirusType.RED);
    private Virus yellowVirus = new Virus(VirusType.YELLOW);

    @BeforeEach
    public void setUp() {
        cityList = createCityList();
        player = new Player(cityList.get(1));
        virusList = Arrays.asList(blueVirus, blackVirus, redVirus, yellowVirus);
        citiesWithResearchCenter = cityList.subList(1,2);
    }

    @Test
    public void getListOfActions_scenario1() {
        //Player is in Atlanta
        //Player without cards
        //Atlanta has research center
        List<Action> actions = getListOfActions(player, virusList, citiesWithResearchCenter, new ArrayList());
        assertEquals(3, actions.size());
        assertEquals(DRIVEFERRY, actions.get(0).getActionsType());
    }

    @Test
    public void getListOfActions_scenario2() {
        //Player is in Atlanta
        //Player with 5 blue cards
        //Atlanta, Algeria has research center
        //Player has Atlanta card
        //There is a receiver player with no cards
        citiesWithResearchCenter = cityList.subList(0,2);
        List<CityCard> listCard = player.getListCard();
        listCard.add(createCityCard(cityList.get(1)));
        listCard.add(createCityCard(cityList.get(9)));
        listCard.add(createCityCard(cityList.get(11)));
        listCard.add(createCityCard(cityList.get(25)));
        listCard.add(createCityCard(cityList.get(24)));

        List<Player> otherPlayersOnTheCity = Arrays.asList(new Player(cityList.get(1)));
        List<Action> actions = getListOfActions(player, virusList, citiesWithResearchCenter, otherPlayersOnTheCity);

        printListOfActions(actions);
        //TO-DO:assertions of the result
    }
}

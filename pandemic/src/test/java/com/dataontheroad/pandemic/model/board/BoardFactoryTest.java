package com.dataontheroad.pandemic.model.board;

import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.dataontheroad.pandemic.model.board.BoardFactory.createBoardWithNumberOfPlayers;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardFactoryTest {

    private final static String INITIAL_CITY = "Atlanta";
    @Test
    void boardFactoryForThreePlayers() throws Exception {
        Board board = createBoardWithNumberOfPlayers(3);

        assertEquals(3, board.getPlayers().size());
        assertEquals(INITIAL_CITY, board.getPlayers().get(0).getCity().getName());
        assertEquals(INITIAL_CITY, board.getPlayers().get(1).getCity().getName());
        assertEquals(INITIAL_CITY, board.getPlayers().get(2).getCity().getName());
        assertEquals(3, board.getPlayers().get(0).getListCard().size());
        assertEquals(48 ,board.getBoardCities().size());

        List<CityCard> infectionDesk = new ArrayList<>(board.getInfectionDiscardDeck());

        City city = board.getCityFromBoardList(infectionDesk.get(0).getCity());
        assertEquals(city.getVirus(), city.getVirusBoxes().get(0));
        city = board.getCityFromBoardList(infectionDesk.get(3).getCity());
        assertEquals(city.getVirus(), city.getVirusBoxes().get(0));
        city = board.getCityFromBoardList(infectionDesk.get(6).getCity());
        assertEquals(city.getVirus(), city.getVirusBoxes().get(0));

        assertEquals(39, board.getInfectionDeck().getInfectionDeck().size());
        assertEquals(9, board.getInfectionDiscardDeck().size());
    }

}
package com.dataontheroad.pandemic.game.service.converters;

import com.dataontheroad.pandemic.game.api.model.turn.TurnResponsePlayer;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.dataontheroad.pandemic.game.service.converters.ConvertTurnDTO.convertTurnResponsePlayer;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertTurnDTOTest {

    Player playerTurnInformation;
    private City atlanta = new City("Atlanta", VirusType.BLUE);
    private City newyork = new City("New York", VirusType.BLUE);
    private City paris = new City("Paris", VirusType.BLUE);

    @Test
    void convertTurnResponsePlayer_success() {
        playerTurnInformation = new Player();
        playerTurnInformation.setCity(atlanta);
        playerTurnInformation.setListCard(Arrays.asList(CityCard.createCityCard(newyork), CityCard.createCityCard(paris)));

        TurnResponsePlayer turnResponsePlayer = convertTurnResponsePlayer(playerTurnInformation);

        assertEquals(atlanta.getName(), turnResponsePlayer.getLocation().getName());
        assertEquals(2,playerTurnInformation.getListCard().size());
        assertEquals(newyork, ((CityCard) playerTurnInformation.getListCard().get(0)).getCity());
        assertEquals(paris, ((CityCard) playerTurnInformation.getListCard().get(1)).getCity());
    }

}
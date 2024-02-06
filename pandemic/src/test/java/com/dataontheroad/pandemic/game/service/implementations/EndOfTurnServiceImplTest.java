package com.dataontheroad.pandemic.game.service.implementations;


import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.model.cards.model.EpidemicCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.ScientistPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_EMPTY_DECK;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class EndOfTurnServiceImplTest {

    private City newyork = new City("New York", VirusType.BLUE);
    private City essen = new City("Essen", VirusType.BLUE);
    @InjectMocks
    EndOfTurnServiceImpl endOfTurnServiceImpl;
    @Mock
    DeckManagementServiceImpl deckManagementService;

    @Test
    void playerGetNewCardsIfIsNotEpidemic_isCityCard() throws EndOfGameException {
        Player scientist = new ScientistPlayer();
        PlayerQueue playerQueue = new PlayerQueue(Arrays.asList(createCityCard(newyork)));
        assertTrue(endOfTurnServiceImpl.getCardsFromPlayerDeck(playerQueue, scientist));
        assertEquals(1, scientist.getListCard().size());
    }

    @Test
    void playerGetNewCardsIfIsNotEpidemic_isEpidemic() throws EndOfGameException {
        Player scientist = new ScientistPlayer();
        PlayerQueue playerQueue = new PlayerQueue(Arrays.asList(new EpidemicCard()));
        assertFalse(endOfTurnServiceImpl.getCardsFromPlayerDeck(playerQueue, scientist));
        assertTrue(scientist.getListCard().isEmpty());
    }

    @Test
    void playerGetNewCardsIfIsNotEpidemic_emptyDeck() {
        Player scientist = new ScientistPlayer();
        PlayerQueue playerQueue = new PlayerQueue(new ArrayList<>());

        EndOfGameException exception =
                assertThrows(EndOfGameException.class,
                        () -> endOfTurnServiceImpl.getCardsFromPlayerDeck(playerQueue, scientist));

        assertTrue(exception.getMessage().contains(END_OF_GAME_EMPTY_DECK));
    }
}
package com.dataontheroad.pandemic.game.service.implementations;


import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.cards.model.EpidemicCard;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.ContingencyPlayer;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.player.ResearchPlayer;
import com.dataontheroad.pandemic.model.player.ScientistPlayer;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_EMPTY_DECK;
import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_EMPTY_INFECTION_DECK;
import static com.dataontheroad.pandemic.model.cards.DeckCardFactory.createInfectionDeck;
import static com.dataontheroad.pandemic.model.cards.model.CityCard.createCityCard;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        assertEquals(exception.getReasonOfEndGame(), END_OF_GAME_EMPTY_DECK);
    }

    @Test
    void setNewTurnAndPlayer_3players_iteratesOnShiftMode() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new ScientistPlayer());
        playerList.add(new ContingencyPlayer());
        playerList.add(new ResearchPlayer());

        TurnInformation turnInformation = new TurnInformation(playerList.get(0));

        for(int i=0; i < 3; i++) {
            assertEquals(playerList.get(0), turnInformation.getActivePlayer());
            endOfTurnServiceImpl.setNewTurnAndPlayer(playerList, turnInformation);
            assertEquals(playerList.get(1), turnInformation.getActivePlayer());
            endOfTurnServiceImpl.setNewTurnAndPlayer(playerList, turnInformation);
            assertEquals(playerList.get(2), turnInformation.getActivePlayer());
            endOfTurnServiceImpl.setNewTurnAndPlayer(playerList, turnInformation);
        }
    }

    @Test
    void getCardsFromInfectionDeck_Iterates_Three_Times() throws EndOfGameException {
        endOfTurnServiceImpl.getCardsFromInfectionDeck(3, createInfectionDeck(), new HashSet<>());
        verify(deckManagementService, times(3)).getCardFromTopInfectionDesk(any(), any());
    }

    @Test
    void getCardsFromInfectionDeck_ThrowException() {
        doThrow(new NoSuchElementException()).
                when(deckManagementService).getCardFromTopInfectionDesk(any(), any());

        EndOfGameException exception =
                assertThrows(EndOfGameException.class,
                        () -> endOfTurnServiceImpl.getCardsFromInfectionDeck(3, createInfectionDeck(), new HashSet<>()));

        assertEquals(exception.getReasonOfEndGame(), END_OF_GAME_EMPTY_INFECTION_DECK);
    }
}
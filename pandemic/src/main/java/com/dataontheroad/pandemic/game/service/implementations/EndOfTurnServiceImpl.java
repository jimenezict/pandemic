package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.game.service.interfaces.IEndOfTurnService;
import com.dataontheroad.pandemic.model.cards.model.BaseCard;
import com.dataontheroad.pandemic.model.cards.model.CityCard;
import com.dataontheroad.pandemic.model.decks.InfectionDeck;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_EMPTY_DECK;
import static com.dataontheroad.pandemic.constants.LiteralGame.END_OF_GAME_EMPTY_INFECTION_DECK;
import static com.dataontheroad.pandemic.game.TurnServiceHelper.getNextActivePlayer;
import static com.dataontheroad.pandemic.model.cards.CardTypeEnum.EPIDEMIC;
import static java.util.Objects.isNull;

@Service
public class EndOfTurnServiceImpl implements IEndOfTurnService {

    private final DeckManagementServiceImpl deckManagementService;

    public EndOfTurnServiceImpl(DeckManagementServiceImpl deckManagementService) {
        this.deckManagementService = deckManagementService;
    }

    @Override
    public boolean getCardsFromPlayerDeck(PlayerQueue playerQueue, Player activePlayer) throws EndOfGameException {
        BaseCard cardToAdd = playerQueue.getCardFromPlayerQueue();
        if(isNull(cardToAdd)) {
            throw new EndOfGameException(END_OF_GAME_EMPTY_DECK);
        } else if(EPIDEMIC.equals(cardToAdd.getCardType())) {
            return false;
        }
        activePlayer.getListCard().add(cardToAdd);
        return true;
    }

    @Override
    public void getCardsFromInfectionDeck(int numberOfInfections, InfectionDeck infectionDeck, Set<CityCard> infectionDiscardDeck) throws EndOfGameException {
        try {
            for (int i = 1; i < numberOfInfections; i++) {
                deckManagementService.getCardFromTopInfectionDesk(infectionDeck, infectionDiscardDeck);
            }
        } catch(NoSuchElementException ex){
            throw new EndOfGameException(END_OF_GAME_EMPTY_INFECTION_DECK);
        }
    }

    @Override
    public void setNewTurnAndPlayer(List<Player> playerList, TurnInformation turnInformation) {
        Player player = getNextActivePlayer(playerList,
                turnInformation.getActivePlayer());
        turnInformation.setNewTurn(player);

    }
}

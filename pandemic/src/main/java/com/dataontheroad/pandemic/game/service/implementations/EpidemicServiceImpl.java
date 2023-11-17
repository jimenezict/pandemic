package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.exceptions.EndOfGameException;
import com.dataontheroad.pandemic.game.service.interfaces.IEpidemicService;
import com.dataontheroad.pandemic.model.decks.PlayerQueue;
import com.dataontheroad.pandemic.model.player.Player;
import org.springframework.stereotype.Service;

import static com.dataontheroad.pandemic.game.TurnServiceHelper.playerGetNewCardsIfIsNotEpidemic;

@Service
public class EpidemicServiceImpl implements IEpidemicService {

    @Override
    public boolean playerGetNewCardsIfIsNotEpidemicAsTimesAsManyTimesAsInfectionsCards(PlayerQueue playerQueue, Player activePlayer, int numberInfectionCard) throws EndOfGameException {
        int iterations = 0;

        do {
            if(!playerGetNewCardsIfIsNotEpidemic(playerQueue, activePlayer))
                return false;
            iterations++;
        } while(iterations <= numberInfectionCard);

        return true;
    }
}

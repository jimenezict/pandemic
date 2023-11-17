package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.exceptions.GameExecutionException;
import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.List;
import java.util.UUID;

public interface IGameService {

    UUID createGame(int numEpidemic, int numPlayers) throws GameExecutionException;

    GameResponseDTO getGameById(UUID uuid);

    interface IEndOfGameService {

        boolean allVirusHadBeenEradicated(List<Virus> virusList);

        VirusType returnVirusIfOverPassTheMaximalNumberOrNull(List<City> listOfCities);
    }
}

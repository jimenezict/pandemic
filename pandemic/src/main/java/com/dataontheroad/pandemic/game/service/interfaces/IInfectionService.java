package com.dataontheroad.pandemic.game.service.interfaces;

import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.List;


public interface IInfectionService {

    VirusType infectCityAndReturnCityVirusTypeIfOverpassOutbreak(City cityFromBoardList);

    boolean canCityBeInfected(City cityToInfect, List<Virus> virusList, List<Player> players);

    void spreadOutbreak(List<Player> players, List<City> nodeCityConnection);
}

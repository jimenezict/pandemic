package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.game.service.interfaces.IGameService;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndOfGameServiceImpl implements IGameService.IEndOfGameService {

    @Override
    public boolean allVirusHadBeenEradicated(List<Virus> virusList) {
        return virusList.stream().filter(Virus::getEradicated).count() == virusList.size();
    }

    @Override
    public VirusType returnVirusIfOverPassTheMaximalNumberOrNull(List<City> listOfCities) {
        return null;
    }
}

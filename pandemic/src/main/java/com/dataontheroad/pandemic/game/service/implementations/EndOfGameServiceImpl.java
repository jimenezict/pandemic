package com.dataontheroad.pandemic.game.service.implementations;

import com.dataontheroad.pandemic.game.service.interfaces.IEndOfGameService;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.virus.Virus;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EndOfGameServiceImpl implements IEndOfGameService {

    private static final long MAX_VIRUS_BOXES = 24L;

    @Override
    public boolean allVirusHadBeenEradicated(List<Virus> virusList) {
        return virusList.stream().filter(Virus::getEradicated).count() == virusList.size();
    }

    @Override
    public VirusType returnVirusIfOverPassTheMaximalNumberOrNull(List<City> listOfCities) {
        ArrayList<VirusType> virusList = new ArrayList<>(Arrays.asList(VirusType.BLUE, VirusType.YELLOW, VirusType.BLACK, VirusType.RED));

        for(VirusType iteratorVirusType : virusList) {
            int numBoxes = listOfCities.stream().mapToInt(city -> numberOfBoxesInCityAndType(city, iteratorVirusType)).sum();
            if(numBoxes > MAX_VIRUS_BOXES)
                return iteratorVirusType;
        }

        return null;
    }

    private int numberOfBoxesInCityAndType(City city, VirusType virusType) {
        return (int) city.getVirusBoxes().stream().filter(cityVirusBox -> cityVirusBox.equals(virusType)).count();
    }

    @Override
    public boolean allCitiesWithoutBoxes(List<City> listOfCities) {
        return listOfCities.stream().filter(city -> !city.getVirusBoxes().isEmpty()).count() == 0;
    }
}

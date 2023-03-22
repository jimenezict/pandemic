package com.dataontheroad.pandemic.actions.services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.model.Action;
import com.dataontheroad.pandemic.actions.model.DiscoverCureAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.Virus;
import com.dataontheroad.pandemic.model.VirusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

public class DiscoverCure {

    public static Boolean isDoable(Player player, Virus virus) {
        return player.getCity().getHasCenter()
                && !virus.getCureDiscovered()
                && playerHasEnoughCars(player, virus.getVirusType());
    }

    public static List<Action> returnAvailableActions(Player player, List<Virus> virusList) {
        List<Virus> virus = virusList
                .stream()
                .filter(x -> isDoable(player, x))
                .collect(Collectors.toList());

        return !isEmpty(virus) ? new ArrayList<>(Arrays.asList(new DiscoverCureAction(player, virus.get(0)))) : new ArrayList<>();
    }

    public static void doAction(Player player, Virus virus) throws ActionException {
        if(!player.getCity().getHasCenter()) {
            throw new ActionException(ActionsType.DISCOVERCURE, "The city do not have a research station");
        } else if (virus.getCureDiscovered()) {
            throw new ActionException(ActionsType.DISCOVERCURE, "The cure has been already discovered");
        } else if (!playerHasEnoughCars(player, virus.getVirusType())) {
            throw new ActionException(ActionsType.DISCOVERCURE, "The player do not have enough cars");
        }

        virus.cureHasBeenDiscovered();
    }

    private static Boolean playerHasEnoughCars(Player player, VirusType virusType) {
        return player.getListCard().stream().filter(x -> x.getVirus().equals(virusType)).count() == 5;
    }

}

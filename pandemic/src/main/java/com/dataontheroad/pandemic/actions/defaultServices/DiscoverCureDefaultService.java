package com.dataontheroad.pandemic.actions.defaultServices;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.actionFactory.Action;
import com.dataontheroad.pandemic.actions.actionFactory.DiscoverCureAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.board.player.Player;
import com.dataontheroad.pandemic.board.model.Virus;
import com.dataontheroad.pandemic.board.model.enums.VirusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static org.springframework.util.CollectionUtils.isEmpty;

public class DiscoverCureDefaultService {

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
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_NO_RESEARCH_STATION);
        } else if (virus.getCureDiscovered()) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_CURE_DISCOVERED);
        } else if (!playerHasEnoughCars(player, virus.getVirusType())) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_NO_CARD);
        }

        virus.cureHasBeenDiscovered();
    }

    private static Boolean playerHasEnoughCars(Player player, VirusType virusType) {
        return player.getListCard().stream().filter(x -> x.getVirus().equals(virusType)).count() == 5;
    }

}

package com.dataontheroad.pandemic.actions.default_services;

import com.dataontheroad.pandemic.actions.ActionsType;
import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DiscoverCureAction;
import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.dataontheroad.pandemic.actions.ActionsHelper.playerHasEnoughCards;
import static com.dataontheroad.pandemic.constants.LiteralsAction.*;
import static org.springframework.util.CollectionUtils.isEmpty;

public class DiscoverCureDefaultService {

    public boolean isDoable(Player player, Virus virus) {
        return player.getCity().getHasCenter()
                && !virus.getCureDiscovered()
                && playerHasEnoughCards(player, virus.getVirusType());
    }

    public List<Action> returnAvailableActions(Player player, List<Virus> virusList) {
        List<Virus> virus = virusList
                .stream()
                .filter(x -> isDoable(player, x))
                .collect(Collectors.toList());

        return !isEmpty(virus) ? new ArrayList<>(Arrays.asList(new DiscoverCureAction(player, virus.get(0)))) : new ArrayList<>();
    }

    public void doAction(Player player, Virus virus) throws ActionException {
        if(!player.getCity().getHasCenter()) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_NO_RESEARCH_STATION);
        } else if (virus.getCureDiscovered()) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_CURE_DISCOVERED);
        } else if (!playerHasEnoughCards(player, virus.getVirusType())) {
            throw new ActionException(ActionsType.DISCOVERCURE, DISCOVERCURE_ERROR_NO_CARD);
        }

        virus.cureHasBeenDiscovered();
        //TO-DO: remove cards from hand
    }



}

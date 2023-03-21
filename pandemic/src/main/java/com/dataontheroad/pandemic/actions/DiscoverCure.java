package com.dataontheroad.pandemic.actions;

import com.dataontheroad.pandemic.exceptions.ActionException;
import com.dataontheroad.pandemic.model.Player;
import com.dataontheroad.pandemic.model.Virus;

public class DiscoverCure {

    public static Boolean isDoable(Player player, Virus virus) {
        return player.getCity().getHasCenter()
                && !virus.getCureDiscovered()
                && playerHasEnoughCars(player, virus);
    }

    public static void doAction(Player player, Virus virus) throws ActionException {
        if(!player.getCity().getHasCenter()) {
            throw new ActionException(ActionsType.DISCOVERCURE, "The city do not have a research station");
        } else if (virus.getCureDiscovered()) {
            throw new ActionException(ActionsType.DISCOVERCURE, "The cure has been already discovered");
        } else if (!playerHasEnoughCars(player, virus)) {
            throw new ActionException(ActionsType.DISCOVERCURE, "The player do not have enough cars");
        }

        virus.cureHasBeenDiscovered();
    }

    private static Boolean playerHasEnoughCars(Player player, Virus virus) {
        return player.getListCard().stream().filter(x -> x.getVirus().equals(virus.getVirusType())).count() == 5;
    }

}

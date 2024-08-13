package com.dataontheroad.pandemic.game;

import com.dataontheroad.pandemic.actions.action_factory.Action;
import com.dataontheroad.pandemic.actions.action_factory.DriveFerryAction;
import com.dataontheroad.pandemic.actions.action_factory.FlyDirectAction;
import com.dataontheroad.pandemic.game.api.model.turn.TurnResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.TurnInformation;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;

import java.util.ArrayList;

import static com.dataontheroad.pandemic.model.city.CityEnum.*;

public class modelBuilder {

    private static final City atlanta = new City(ATLANTA.cityName, VirusType.BLUE);
    private static final City washington = new City(WASHINGTON.cityName, VirusType.BLUE);
    private static final City chicago = new City(CHICAGO.cityName, VirusType.BLUE);
    private static final City miami = new City(MIAMI.cityName, VirusType.BLUE);
    private static final City madrid = new City(MADRID.cityName, VirusType.BLUE);
    private static final City paris = new City(PARIS.cityName, VirusType.BLUE);

    public static TurnResponseDTO buildTurnResponseDTOWithActionList(Player player) {
        player.setCity(atlanta);
        TurnInformation turnInformation = new TurnInformation(player);
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new DriveFerryAction(player, washington));
        actionList.add(new DriveFerryAction(player, chicago));
        actionList.add(new DriveFerryAction(player, miami));
        actionList.add(new FlyDirectAction(player, madrid));
        actionList.add(new FlyDirectAction(player, paris));
        TurnResponseDTO turnResponseDTO = new TurnResponseDTO();
        turnResponseDTO.setTurnInformation(turnInformation, actionList);

        return turnResponseDTO;
    }
}

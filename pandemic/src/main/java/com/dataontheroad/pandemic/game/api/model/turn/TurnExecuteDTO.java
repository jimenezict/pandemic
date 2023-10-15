package com.dataontheroad.pandemic.game.api.model.turn;

import com.dataontheroad.pandemic.game.TurnServiceHelper;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.board.Board;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.Virus;

import java.util.List;

import static com.dataontheroad.pandemic.game.TurnServiceHelper.getCitiesWithResearchCenter;

public class TurnExecuteDTO {
    private GameDTO gameDTO;
    public TurnExecuteDTO(GameDTO gameDTO) {
        this.gameDTO = gameDTO;
    }

    public Player getActivePlayer() {
        return gameDTO.getTurnInformation().getActivePlayer();
    }

    public List<Virus> getVirusList() {
        return gameDTO.getBoard().getVirusList();
    }

    public List<City> getBoardCities() {
        return gameDTO.getBoard().getBoardCities();
    }

    public List<City> getResearchCenter() {
        return getCitiesWithResearchCenter(gameDTO);
    }

    public List<Player> getOtherPlayersOnTheCity() {
        return TurnServiceHelper.getOtherPlayersOnTheCity(gameDTO);
    }

    public Board getBoard() {
        return gameDTO.getBoard();
    }
}

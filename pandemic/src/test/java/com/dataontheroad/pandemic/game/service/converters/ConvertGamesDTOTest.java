package com.dataontheroad.pandemic.game.service.converters;

import com.dataontheroad.pandemic.game.api.model.game.GameResponseDTO;
import com.dataontheroad.pandemic.game.persistence.model.GameDTO;
import com.dataontheroad.pandemic.model.city.City;
import com.dataontheroad.pandemic.model.player.Player;
import com.dataontheroad.pandemic.model.virus.VirusType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static com.dataontheroad.pandemic.model.virus.VirusType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertGamesDTOTest {

    @ParameterizedTest
    @CsvSource({
            "2, 4",
            "3, 3",
            "4, 2",
    })
    void convertGameDTO(int numberOfPlayers, int numberOfCards) throws Exception {

        GameDTO gameDTO = new GameDTO(numberOfPlayers);
        GameResponseDTO gameResponseDTO = ConvertGamesDTO.convertGameDTO(gameDTO);

        //Validations of the ID of the game
        assertEquals(gameDTO.getUuid(), gameResponseDTO.getGameId());

        //Validations about number of players on the board
        assertEquals(gameDTO.getBoard().getPlayers().size(), gameResponseDTO.getGameResponsePlayerList().size());
        assertEquals(numberOfPlayers, gameResponseDTO.getGameResponsePlayerList().size());

        //Validations about player content
        // ** gameResponseDTO is the entity after the conversion
        // ** gameDTO is the entity before teh conversion
        // Checks for the number of cards, the name of the player and location
        gameResponseDTO.getGameResponsePlayerList().stream().forEach(gameResponseDTOplayer -> {
            Player gameDTOplayer = gameDTO.getBoard().getPlayers().stream().filter(x -> x.getName().equals(gameResponseDTOplayer.getName())).findFirst().get();
            assertEquals(numberOfCards, gameResponseDTOplayer.getListCard().size());
            assertEquals(gameResponseDTOplayer.getName(), gameDTOplayer.getName());
            assertEquals(gameResponseDTOplayer.getLocation().getName(), gameDTOplayer.getCity().getName());
        });

        //Validations about city content
        // Checks for the number and types of virus for each city
        // Note: this do not needs ot run for all the parameterized, as the output is the same, but is not a big issue on performance
        gameResponseDTO.getGameResponseCityList().forEach((city,virusTypeList) -> {
            List<VirusType> virusTypeOnCityGameDTO = gameDTO.getBoard().getCityFromBoardList(new City(city, null)).getVirusBoxes();
            assertEquals(virusTypeList.stream().filter(virusType -> BLUE.equals(virusType)).count(),
                    virusTypeOnCityGameDTO.stream().filter(virusType -> BLUE.equals(virusType)).count());
            assertEquals(virusTypeList.stream().filter(virusType -> RED.equals(virusType)).count(),
                    virusTypeOnCityGameDTO.stream().filter(virusType -> RED.equals(virusType)).count());
            assertEquals(virusTypeList.stream().filter(virusType -> BLACK.equals(virusType)).count(),
                    virusTypeOnCityGameDTO.stream().filter(virusType -> BLACK.equals(virusType)).count());
            assertEquals(virusTypeList.stream().filter(virusType -> YELLOW.equals(virusType)).count(),
                    virusTypeOnCityGameDTO.stream().filter(virusType -> YELLOW.equals(virusType)).count());
        });

        assertEquals(1, gameResponseDTO.getCitiesWithLab().size());
    }

}
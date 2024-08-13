package be.howest.ti.game.logic;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabyrinthServiceImplTest {
    private LabyrinthServiceImpl labyrinthService;

    @BeforeEach
    void setUp() {
        labyrinthService = new LabyrinthServiceImpl();
    }

    @Test
    void configureGame() {
        Game game = labyrinthService.configureGame("gameName", "prefix", "playerName", "simple", 2, 3, 4);
        assertEquals("prefix-gameName", game.getGameId().toString());
    }

    @Test
    void checkIfGameAlreadyExists() {
        Game game = labyrinthService.configureGame("gameName", "prefix", "playerName", "simple", 2, 3, 4);
        assertTrue(labyrinthService.checkIfGameAlreadyExists(game));

        Game newGame = new Game(new GameId("newPrefix", "newPlayerName"), "newGameMode", 2, 3, 4);
        assertFalse(labyrinthService.checkIfGameAlreadyExists(newGame));
    }

    @Test
    void testGameByPrefix(){
        Game game = labyrinthService.configureGame("gameName", "PrefixTest", "playerName", "simple", 2, 3, 4);
        assertEquals("PrefixTest", game.getGameId().getPrefix());
    }
}
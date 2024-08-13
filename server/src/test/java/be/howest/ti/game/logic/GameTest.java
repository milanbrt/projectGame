package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        GameId gameId = new GameId("prefix", "playerName");
        game = new Game(gameId, "gameMode", 2, 4, 3);
    }

    @Test
    void testGameId() {
        assertEquals("prefix-playerName", game.getGameId().toString());
    }

    @Test
    void testPlayer() {
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer1"));
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer2"));

        assertEquals(2, game.getPlayers().size());
    }
    @Test
    void removePlayer(){
        Player player = new Player("gameId", "playerName");
        game.addPlayer(player);
        assertTrue(game.getPlayers().contains(player));
        game.removePlayer(player);
        assertFalse(game.getPlayers().contains(player));
    }

    @Test
    void testGameDoesNotStartWithLessThanMinPlayers() {
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer1"));
        assertFalse(game.getIsGameStarted());
    }

    @Test
    void testGameStartsWithMinPlayers() {
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer1"));
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer2"));
        assertTrue(game.getIsGameStarted());
    }

    @Test
    void testGameDoesNotAllowMoreThanMaxPlayers() {
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer1"));
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer2"));
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer3"));
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer4"));

        assertThrows(IllegalStateException.class, () -> game.addPlayer(new Player("prefix-playerName", "IAmPlayer5")));
    }

    @Test
    void testPlayerNameStartsWithLetter() {
        assertTrue(game.startsWithLetter("IAmPlayer1"));
        assertFalse(game.startsWithLetter("1AmPlayer1"));
    }

    @Test
    void testGameEquals() {
        GameId gameId = new GameId("prefix", "playerName");
        Game game1 = new Game(gameId, "gameMode", 2, 4, 3);
        Game game2 = new Game(gameId, "gameMode", 2, 4, 3);
        Game game3 = new Game(new GameId("prefix", "playerName"), "gameMode", 2, 4, 3);

        assertEquals(game1, game2);
        assertEquals(game1, game3);
    }
}
package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class JoinGameTest {
    @Test
    void testJoinGame() {
        GameId gameId = new GameId("prefix", "playerName");
        Game game = new Game(gameId, "gameMode", 1, 2, 3);
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer1"));
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer2"));
        assertEquals(2, game.getPlayers().size());

    }

    @Test
    void testGameId() {
        GameId gameId = new GameId("prefix", "playerName");
        Game game = new Game(gameId, "gameMode", 1, 2, 3);
        assertEquals("prefix-playerName", game.getGameId().toString());
    }




}

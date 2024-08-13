package be.howest.ti.game.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    private Game game;
    private Maze maze;

    @BeforeEach
    void setUp() {
        game = new Game(new GameId("prefix", "playerName"), "gameMode", 1, 2, 3);
        game.addPlayer(new Player("prefix-playerName", "IAmPlayer1")); // Add a player to the game
        game.setGameStarted();
        maze = game.getMaze();

        maze.getTiles()[0][0] = new Tile("null", new boolean[]{true, true, false, false}, null, new Location(0, 0)); // East
        maze.getTiles()[0][0].setLocation(new Location(0, 0));

        maze.getTiles()[0][1] = new Tile("null", new boolean[]{true, true, false, false}, null, new Location(0, 1)); // South and West
        maze.getTiles()[0][0].setLocation(new Location(0, 1));

        maze.getTiles()[1][0] = new Tile("null", new boolean[]{false, false, true, true}, null, new Location(1, 0)); // North and East
        maze.getTiles()[0][0].setLocation(new Location(1, 0));

        maze.getTiles()[1][1] = new Tile("null", new boolean[]{false, true, true, false}, null, new Location(1, 1)); // North and West
        maze.getTiles()[0][0].setLocation(new Location(1, 1));
    }

    @Test
    void testReachableTilesFromTopLeftCorner() {
        Set<Tile> reachableTiles = maze.getReachableTiles(maze.getTiles()[0][0]);
        assertTrue(reachableTiles.contains(maze.getTiles()[0][1]));
        assertTrue(reachableTiles.contains(maze.getTiles()[1][0]));
    }

    @Test
    void testReachableTilesFromTopRightCorner() {
        Set<Tile> reachableTiles = maze.getReachableTiles(maze.getTiles()[0][1]);
        assertTrue(reachableTiles.contains(maze.getTiles()[0][0]));
        assertTrue(reachableTiles.contains(maze.getTiles()[1][1]));
    }

    @Test
    void testReachableTilesFromBottomLeftCorner() {
        Set<Tile> reachableTiles = maze.getReachableTiles(maze.getTiles()[1][0]);
        assertTrue(reachableTiles.contains(maze.getTiles()[0][0]));
        assertTrue(reachableTiles.contains(maze.getTiles()[1][1]));
    }

    @Test
    void testReachableTilesFromBottomRightCorner() {
        Set<Tile> reachableTiles = maze.getReachableTiles(maze.getTiles()[1][1]);
        assertTrue(reachableTiles.contains(maze.getTiles()[0][1]));
        assertTrue(reachableTiles.contains(maze.getTiles()[1][0]));
    }

    @Test
    void getMaze() {
        assertNotNull(maze.getMazeBridge(), "Maze should not be null");
        assertEquals(7, maze.getMazeBridge().size(), "Maze should have 7 rows");
        for (List<Map<String, Object>> row : maze.getMazeBridge()) {
            assertEquals(7, row.size(), "Each row should have 7 tiles");
        }
    }

    @Test
    void getSpareTile() {
        assertNotNull(maze.getSpareTile(), "Spare tile should not be null");
        assertTrue(maze.getSpareTile() instanceof Tile, "Spare tile should be instance of SpareTile");
    }

    @Test
    void shoveTileInWrongLocation() {
        Location location = new Location(0, 0);
        Location location2 = new Location(2,0);
        Location location3 = new Location(0,2);
        Location location4 = new Location(6,6);
        Location location5 = new Location(3,3);
        Player player = new Player("tano", "tano");
        Tile newTile = new Tile("treasure", new boolean[]{true, true, false, false}, new ArrayList<>(), new Location(0, 0));
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location, newTile, player, game), "Should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location2, newTile, player, game), "Should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location3, newTile, player, game), "Should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location4, newTile, player, game), "Should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location5, newTile, player, game), "Should throw IllegalArgumentException");
    }


    @Test
    void testShoveTileNotPlayersTurn() {
        Location location = new Location(0, 1);
        Player player = new Player("otherPlayer", "otherPlayer");
        Tile newTile = new Tile("treasure", new boolean[]{true, true, false, false}, new ArrayList<>(), new Location(0, 0));
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location, newTile, player, game), "Should throw IllegalArgumentException because it's not the player's turn");
    }


    @Test
    void testShoveTileFixedLocation() {
        Location location = new Location(0, 0);
        Player player = new Player("tano", "tano");
        Tile newTile = new Tile("treasure", new boolean[]{true, true, false, false}, new ArrayList<>(), new Location(0, 0));
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location, newTile, player, game), "Should throw IllegalArgumentException because tile is being shoved into a fixed location");
    }

    @Test
    void testShoveTileNotOnEdge() {
        Location location = new Location(1, 1);
        Player player = new Player("tano", "tano");
        Tile newTile = new Tile("treasure", new boolean[]{true, true, false, false}, new ArrayList<>(), new Location(0, 0));
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location, newTile, player, game), "Should throw IllegalArgumentException because tile is being shoved into a location that is not on the edge of the board");
    }


    @Test
    void testShoveTilePlayerNotInGame() {
        Location location = new Location(0, 1);
        Player player = new Player("otherPlayer", "otherPlayer");
        game.getPlayers().remove(player);
        Tile newTile = new Tile("treasure", new boolean[]{true, true, false, false}, new ArrayList<>(), new Location(0, 0));
        assertThrows(IllegalArgumentException.class, () -> maze.shoveTile(location, newTile, player, game), "Should throw IllegalArgumentException because player is not in the game");
    }
}
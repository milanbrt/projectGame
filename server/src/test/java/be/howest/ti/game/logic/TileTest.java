package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest {

    @Test
    void testGetTreasure() {
        String expectedTreasure = "gold";
        boolean[] walls = new boolean[]{false, false, false, false};
        List<String> players = new ArrayList<>();
        Location location = new Location(0, 0);
        Tile tile = new Tile(expectedTreasure, walls, players, location);

        String actualTreasure = tile.getTreasure();

        assertEquals(expectedTreasure, actualTreasure, "The treasure should be gold");
    }

    @Test
    void testGetWalls() {
        boolean[] expectedWalls = new boolean[]{true, true, true, true};
        List<String> players = new ArrayList<>();
        Location location = new Location(0, 0);
        Tile tile = new Tile("gold", expectedWalls, players, location);

        boolean[] actualWalls = tile.getWalls();

        assertEquals(expectedWalls, actualWalls, "The walls should be true, true, true, true");
    }

    @Test
    void testGetPlayers() {
        String player1 = "player1";
        String player2 = "player2";
        List<String> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(player1);
        expectedPlayers.add(player2);
        boolean[] walls = new boolean[]{false, false, false, false};
        Location location = new Location(0, 0);
        Tile tile = new Tile("gold", walls, expectedPlayers, location);

        List<String> actualPlayers = tile.getPlayers();

        assertEquals(expectedPlayers, actualPlayers, "The players should be player1 and player2");
    }

    @Test
    void testGetLocation() {
        Location expectedLocation = new Location(0, 0);
        boolean[] walls = new boolean[]{false, false, false, false};
        List<String> players = new ArrayList<>();
        Tile tile = new Tile("gold", walls, players, expectedLocation);

        Location actualLocation = tile.getLocation();

        assertEquals(expectedLocation, actualLocation, "The location should be (0, 0)");
    }

    @Test
    void testSetTreasure() {
        String expectedTreasure = "gold";
        boolean[] walls = new boolean[]{false, false, false, false};
        List<String> players = new ArrayList<>();
        Location location = new Location(0, 0);
        Tile tile = new Tile("null", walls, players, location);

        tile.setTreasure(expectedTreasure);

        assertEquals(expectedTreasure, tile.getTreasure(), "The treasure should be gold");
    }

}


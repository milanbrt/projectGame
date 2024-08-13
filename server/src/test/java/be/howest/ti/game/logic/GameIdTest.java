package be.howest.ti.game.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameIdTest {

    @Test
    void testGameIdsFromTheSameStringAreEqual(){
        GameId id1 = new GameId("test", "test");
        GameId id2 = new GameId("test", "test");
        assertEquals(id1, id2);
    }

}

package be.howest.ti.game.web.views.response;

import be.howest.ti.game.logic.Maze;
import be.howest.ti.game.logic.Tile;

import java.util.HashMap;
import java.util.Map;

public class ShoveTileResponse extends ResponseWithHiddenStatus{
    private final Maze maze;
    private final Tile tile;
    public ShoveTileResponse(Maze maze, Tile tile) {
        super(200);
        this.maze = maze;
        this.tile = tile;
    }

    public Map<String, Object> getMaze() {
        Map<String, Object> mazeMap = new HashMap<>();
        mazeMap.put("rows", 0);
        mazeMap.put("columns", 0);
        mazeMap.put("board", maze.getMazeBridge());
        return mazeMap;
    }

    public Map<String, Object> getTile() {
        Map<String, Object> tileMap = new HashMap<>();
        tileMap.put("walls", tile.getWalls());
        tileMap.put("treasure", tile.getTreasure());
        tileMap.put("players", tile.getPlayers());
        return tileMap;
    }
}

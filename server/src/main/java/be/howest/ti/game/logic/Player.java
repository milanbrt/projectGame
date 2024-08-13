package be.howest.ti.game.logic;

import be.howest.ti.game.web.LabyrinthUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player extends LabyrinthUser {
    private PlayerName playerName;
    private Location location;
    private String currentTreasure;
    public String state;
    private List<String> treasures;
    public Player(String gameId, String playerName) {
        super(gameId, playerName);
        this.playerName = new PlayerName(playerName);
        this.treasures = new ArrayList<>();
    }

    public Player(LabyrinthUser user){
        super(user.getGameId(), user.getPlayerName());
        this.playerName = new PlayerName(user.getPlayerName().toString());
        this.treasures = new ArrayList<>();
    }

    public boolean hasMaxTreasures(int maxTreasures) {
        return treasures.size() == maxTreasures;
    }

    public void setLocation(Location location, Maze maze) {
        this.location = location;
        Tile tile = maze.getTile(location);
        if (tile != null && tile.getTreasure() != null && tile.getTreasure().equals(this.currentTreasure)) {
            foundTreasure(tile.getTreasure(), maze);
        }
    }



    private void foundTreasure(String treasure, Maze maze) {
        this.treasures.add(treasure);
        currentTreasure = maze.getRandomTreasure();
    }

    public void setPlayerName(PlayerName playerName) {
        this.playerName = playerName;
    }

    public Location getLocation(){
        return location;
    }

    public List<String> getTreasures(){
        return treasures;
    }

    public void setTreasure(String treasure){
        this.currentTreasure = treasure;
    }

    public String getState(){
        return "WAINTING";
    }

    public String getObjective(){
        return currentTreasure;
    }


    public Map<String, Object> playerDetails(){
        String name = this.playerName != null ? this.playerName.toString() : "";
        Location locationPlayer = this.location != null ? this.location : new Location(0, 0); // assuming default location (0,0)
        List<String> treasuresPlayer = this.treasures != null ? this.treasures : new ArrayList<>();
        String statePlayer = this.getState() != null ? this.getState() : "";
        String objective = this.getObjective() != null ? this.getObjective() : "";

        return Map.of(
                "name", name,
                "location", locationPlayer,
                "treasures", treasuresPlayer,
                "state", statePlayer,
                "objective", objective
        );
    }
}
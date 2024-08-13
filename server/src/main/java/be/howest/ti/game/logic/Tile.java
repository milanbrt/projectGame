package be.howest.ti.game.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Tile {
    private String treasure;
    private boolean[] walls;
    private List<String> players;
    private Location location;


    public Tile(String treasure, boolean[] walls, List<String> players, Location location) {
        this.treasure = treasure;
        this.walls = walls;
        this.players = players;
        if (location == null) {
            this.location = new Location(10, 10); // default location
        } else {
            this.location = location;
        }
    }

    public String getTreasure() {
        return treasure;
    }

    public void setTreasure(String treasure) {
        this.treasure = treasure;
    }
    public void setLocation (Location location){
        this.location = location;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public List<String> getPlayers() {
        return players;
    }

    public Location getLocation(){
        return location;
    }

    public Map<String, Object> getSpareTileInfo() {
        String treasureSpareTile = this.getTreasure();
        boolean[] wallsSpareTIle = this.getWalls();
        if (wallsSpareTIle == null) {
            throw new IllegalArgumentException("Walls cannot be null");
        }
        if (treasure == null) {
            return Map.of("walls", wallsSpareTIle);
        } else {
            return Map.of(
                    "treasure", treasureSpareTile,
                    "walls", wallsSpareTIle
            );
        }
    }

    public List<Player> removeAllPlayers() {
        List<Player> playersList = new ArrayList<>();
        if (players != null) {
            for (String player : players) {
                playersList.add(new Player(player, player));
            }
            players = new ArrayList<>();
        }
        return playersList;
    }

    public void removePlayer(Player playerName) {
        if (players != null) {
            players.remove(playerName.getPlayerName().toString());
        }
    }


    public String toString() {
        return "Tile{" +
                "treasure='" + treasure + '\'' +
                ", walls=" + Arrays.toString(walls) +
                ", players=" + players +
                ", location=" + location.toString() +
                '}' + "\n";
    }

    public void addPlayer(Player player) {
        if (players == null) {
            players = new ArrayList<>();
            players.add(player.getPlayerName().toString());
        } else {
            players.add(player.getPlayerName().toString());
        }
    }

    public void addPlayers(List<Player> players) {
        if (players == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        for (Player player : players) {
            addPlayer(player);
        }
    }
}

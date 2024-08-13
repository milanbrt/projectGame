package be.howest.ti.game.logic;

import be.howest.ti.game.web.LabyrinthUser;

import java.security.SecureRandom;
import java.util.*;

public class Game {

    private final GameId gameId;
    private final List<Player> players;
    private Maze maze;
    private final int minPlayers;
    private final int maxPlayers;
    private final int maxTreasures;
    private final String gameMode;
    private final SecureRandom rand = new SecureRandom();
    private boolean isGameStarted = false;

    private String currentShovePlayer;
    private String currentMovePlayer;
    private GameState gameState;

    List<Location> startLocations = new ArrayList<>();


    public Game(GameId gameId, String gameMode, int minPlayers, int maxPlayers, int maxTreasures) {
        this.gameId = gameId;
        this.gameMode = gameMode;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.maxTreasures = maxTreasures;
        this.players = new ArrayList<>();

    }


    public String getCurrentShovePlayer() {
        return currentShovePlayer;
    }

    private String getGameMode(){
        return gameMode;
    }

    public String getGameStatus(Player player){
        if (player.hasMaxTreasures(maxTreasures) && getGameMode() != null){
            return "WON";
        } else if (currentShovePlayer == null || currentShovePlayer.equals(player.getPlayerName().toString())){
            return "SHOVING";
        } else if (currentMovePlayer == null || currentMovePlayer.equals(player.getPlayerName().toString())){
            return "MOVING";
        } else {
            return "WAITING";
        }
    }

    public Player getPlayerByName(String playerName){
        for (Player player : players) {
            if (player.getPlayerName().toString().equals(playerName)){
                return player;
            }
        }
        return null;
    }


    public void giveTurnToPlayer(Player player) {
        if (!checkIfPlayerAlreadyExists(player)) {
            throw new IllegalArgumentException("Player does not exist");
        }


        if (gameState == GameState.SHOVE) {
            gameState = GameState.MOVE;
            currentMovePlayer = player.getPlayerName().toString();
            currentShovePlayer = null;
        } else {
            gameState = GameState.SHOVE;
            currentShovePlayer = player.getPlayerName().toString();
            currentMovePlayer = null;
        }
    }


    public Player getNextPlayer() {
        int currentPlayerIndex = players.indexOf(getPlayerByName(currentMovePlayer));
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }

    public void addPlayer(Player player) {
        if (players.size() >= maxPlayers) {
            throw new IllegalStateException("Cannot join game, maximum number of players reached");
        }
        if (checkIfPlayerAlreadyExists(player)) {
            throw new IllegalArgumentException("Player already exists");
        }
        if (!startsWithLetter(player.getPlayerName().toString())) {
            throw new IllegalArgumentException("Player name must start with a letter");
        }


        this.players.add(player);

        checkIfGameCanStart();

        if (currentShovePlayer == null) {
            currentShovePlayer = player.getPlayerName().toString();
        }
    }

    private void startGame(){
        this.maze = new Maze();

        this.gameState = GameState.SHOVE;
        currentMovePlayer = null;
        currentShovePlayer = null;

        startLocations.add(new Location(0, 0));
        startLocations.add(new Location(0, 6));
        startLocations.add(new Location(6, 0));
        startLocations.add(new Location(6, 6));

        for (Player player : players) {
            Location randomLocation = startLocations.get(rand.nextInt(startLocations.size()));
            player.setLocation(randomLocation, getMaze());
            startLocations.remove(randomLocation);
            player.setTreasure(getMaze().treasures.pollLast());
            getMaze().getTiles()[randomLocation.getRow()][randomLocation.getCol()].addPlayer(player);
        }

    }

    public void setGameStarted(){
        isGameStarted = true;
    }

    private void checkIfGameCanStart() {
        if (players.size() >= minPlayers) {
            isGameStarted = true;
            startGame();
        }
    }

    public boolean getIsGameStarted(){
        return isGameStarted;
    }


    public void removePlayer(Player player) {
        if (!checkIfPlayerAlreadyExists(player)) {
            throw new IllegalArgumentException("Player does not exist");
        }
        startLocations.add(player.getLocation());

        players.remove(player);
    }

    private Boolean checkIfPlayerAlreadyExists(Player playerToCheck) {
        for (Player player : players) {
            if (player.equals(playerToCheck)) {
                return true;
            }
        }
        return false;
    }

    public boolean startsWithLetter(String playerName) {
        return playerName.matches("^[a-zA-Z].*");
    }

    public List<Player> getPlayers(){
        return players;
    }

    public GameId getGameId(){
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equals(gameId, game.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId);
    }


    public void removePlayer(LabyrinthUser user) {
        players.remove(user);
    }

    public List<String> playersToString(){
        List<Player> allPlayers = getPlayers();
        List<String> newPlayers = new ArrayList<>();
        for (Player player : allPlayers) {
            newPlayers.add(player.toString());
        }
        return newPlayers;
    }

    public Map<String, Object> getGamesRunning() {
        Map<String, Object> gameInfo = new HashMap<>();
        gameInfo.put("started", false);
        gameInfo.put("gameName", gameId.getGameName());
        gameInfo.put("gameMode", "SIMPLE");
        gameInfo.put("players", playersToString());
        gameInfo.put("maxPlayers", maxPlayers);
        gameInfo.put("minPlayers", minPlayers);
        gameInfo.put("numberOfTreasuresPerPlayer", maxTreasures);
        gameInfo.put("currentShovePlayer", currentShovePlayer);
        gameInfo.put("currentMovePlayer", currentMovePlayer);
        gameInfo.put("id", gameId.toString());

        return gameInfo;
    }
    public Map<String, Object> getPlayerDetails(Player player){
        Map<String, Object> playerInfo = new HashMap<>();
        playerInfo.put("name", player.getPlayerName().toString());
        playerInfo.put("location", player.getLocation());
        playerInfo.put("foundTreasures", player.getTreasures());
        playerInfo.put("state", getGameStatus(player));
        playerInfo.put("objective", player.getObjective());
        return playerInfo;
    }



    public Map<String, Object> getAllPlayerDetails(){
        Map<String, Object> allPlayerInfo = new HashMap<>();
        for (Player player : players) {
            allPlayerInfo.put(player.getPlayerName().toString(), getPlayerDetails(player));
        }
        return allPlayerInfo;
    }

    public Maze getMaze(){
        return maze;
    }
}
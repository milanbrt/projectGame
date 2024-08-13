package be.howest.ti.game.logic;
import java.util.ArrayList;
import java.util.List;

public class LabyrinthServiceImpl implements LabyrinthService {
    private final List<Game> games = new ArrayList<>();

    @Override
    public Game configureGame(String gameName, String prefix, String playerName, String gameMode, int minPlayers, int maxPlayers, int maxTreasures) {
        GameId gameId = new GameId(prefix, gameName);
        Game game = new Game(gameId, gameMode, minPlayers, maxPlayers, maxTreasures);

        if (Boolean.TRUE.equals(checkIfGameAlreadyExists(game))) {
            throw new IllegalArgumentException("Game already exists");
        }

        games.add(game);
        Player player = new Player(game.getGameId().toString(), playerName);
        game.addPlayer(player);
        return game;
    }

    public Boolean checkIfGameAlreadyExists(Game gameToCheck) {
        for (Game game : games) {
            if (game.equals(gameToCheck)) {
                return true;
            }
        }
        return false;
    }

    public Game getGameById(String gameId) {
        for (Game game : games) {
            if (game.getGameId().toString().equals(gameId)) {
                return game;
            }
        }
        return null;
    }


    public List<Game> getGames() {
        return games;
    }

    public List<Game> getGamesByPrefix(String prefix){
        List<Game> gamesByPrefix = new ArrayList<>();
        for (Game game : games) {
            if (game.getGameId().getPrefix().toLowerCase().equals(prefix)) {
                gamesByPrefix.add(game);
            }
        }
        return gamesByPrefix;
    }
}

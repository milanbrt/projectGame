package be.howest.ti.game.logic;

import java.util.List;

public interface LabyrinthService {
    Game configureGame(String gameName, String prefix, String playerName, String gameMode, int minPlayers, int maxPlayers, int maxTreasures);
    Game getGameById(String gameId);
    List<Game> getGames();
    List<Game> getGamesByPrefix(String prefix);
}
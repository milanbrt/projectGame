package be.howest.ti.game.web;

import be.howest.ti.game.logic.GameId;
import be.howest.ti.game.logic.PlayerName;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.impl.UserImpl;

public class LabyrinthUser extends UserImpl {

    public LabyrinthUser(String gameId, String playerName) {
        super(
                new JsonObject().put("gameId", gameId).put("playerName", playerName),
                new JsonObject()
        );
    }
    public LabyrinthUser(GameId gameId, PlayerName playerName) {
        this(gameId.toString(), playerName.toString());
    }

    public GameId getGameId() {
        try {
            String[] parts = super.principal().getString("gameId").split("-");
            return new GameId(parts[0], parts[1]);
        } catch (IllegalArgumentException e) {
            throw new ForbiddenAccessException("Invalid gameId");
        }
    }

    public PlayerName getPlayerName() {
        return new PlayerName(super.principal().getString("playerName"));
    }

    public String toString() {
        return getPlayerName().toString();
    }
}

package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class CreateGameRequest extends ContextBasedRequestView {
    public CreateGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getPrefix() {
        return params.body().getJsonObject().getString("prefix");
    }

    public String getGameName() {
        return params.body().getJsonObject().getString("gameName");
    }

    public String getPlayerName() {
        return params.body().getJsonObject().getString("playerName");
    }

    public String getGameMode() {
        return params.body().getJsonObject().getString("gameMode");
    }

    public int getMinPlayers() {
        Integer minPlayers = params.body().getJsonObject().getInteger("minimumPlayers");
        if (minPlayers == null) {
            minPlayers = 2;
        }
        return minPlayers;
    }

    public int getMaxPlayers() {
        Integer maxPlayers = params.body().getJsonObject().getInteger("maximum");
        if (maxPlayers == null) {
            maxPlayers = 6;
        }
        return maxPlayers;
    }

    public int getMaxTreasures() {
        Integer maxTreasures = params.body().getJsonObject().getInteger("maximumTreasures");
        if (maxTreasures == null) {
            maxTreasures = 6;
        }
        return maxTreasures;

    }
}

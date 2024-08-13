package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class JoinGameRequest extends ContextBasedRequestView {
    public JoinGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getGameId() {
        return params.pathParameter("gameId").getString();
    }

    public String getPlayerName() {
        return params.pathParameter("playerName").getString();
    }
}

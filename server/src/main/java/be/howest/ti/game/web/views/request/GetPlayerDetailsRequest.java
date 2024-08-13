package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class GetPlayerDetailsRequest extends ContextBasedRequestView {
    public GetPlayerDetailsRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getGameId(){
        return params.pathParameter("gameId").getString();
    }

    public String getPlayerName(){
        return params.pathParameter("playerName").getString();
    }
}

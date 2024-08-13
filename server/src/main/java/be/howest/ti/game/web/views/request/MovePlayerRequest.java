package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Location;
import io.vertx.ext.web.RoutingContext;

public class MovePlayerRequest extends ContextBasedRequestView {
    public MovePlayerRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getGameId(){
        return params.pathParameter("gameId").getString();
    }
    public String getPlayerName(){
        return params.pathParameter("playerName").getString();
    }

    public Location getDestination() {
        return params.body().getJsonObject().getJsonObject("destination").mapTo(Location.class);
    }
}

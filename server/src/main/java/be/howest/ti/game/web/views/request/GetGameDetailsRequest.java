package be.howest.ti.game.web.views.request;

import io.vertx.ext.web.RoutingContext;

public class GetGameDetailsRequest extends ContextBasedRequestView {
    public GetGameDetailsRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getGameId(){
        return params.pathParameter("gameId").getString();
    }

    public Boolean getDescription() {
        Boolean description = params.queryParameter("description").getBoolean();
        return description != null ? description : true;
    }

    public Boolean getPlayers() {
        Boolean players = params.queryParameter("players").getBoolean();
        return players != null ? players : false;
    }

    public Boolean getMaze() {
        Boolean maze = params.queryParameter("maze").getBoolean();
        return maze != null ? maze : false;
    }

    public Boolean getSpareTile() {
        Boolean spareTile = params.queryParameter("spareTile").getBoolean();
        return spareTile != null ? spareTile : false;
    }

    public Boolean getHistory() {
        Boolean history = params.queryParameter("history").getBoolean();
        return history != null ? history : false;
    }

    public Boolean getInitialMaze() {
        Boolean initialMaze = params.queryParameter("initialMaze").getBoolean();
        return initialMaze != null ? initialMaze : false;
    }
}

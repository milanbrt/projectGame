package be.howest.ti.game.web.views.request;

import be.howest.ti.game.logic.Location;
import be.howest.ti.game.logic.Player;
import be.howest.ti.game.logic.Tile;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;


public class ShoveTileRequest extends ContextBasedRequestView {
    public ShoveTileRequest(RoutingContext ctx) {
        super(ctx);
    }

    public String getGameId(){
        return params.pathParameter("gameId").getString();
    }

    public Location getDestination() {
        return params.body().getJsonObject().getJsonObject("destination").mapTo(Location.class);
    }

    public Tile getTile() {
        JsonObject tile = params.body().getJsonObject().getJsonObject("tile");
        Location location = getDestination();

        JsonArray wallsJsonArray = tile.getJsonArray("walls");
        boolean[] wallsArray = null;
        if (wallsJsonArray != null) {
            List<Boolean> wallsList = wallsJsonArray.getList();
            wallsArray = new boolean[wallsList.size()];
            for (int i = 0; i < wallsList.size(); i++) {
                wallsArray[i] = wallsList.get(i);
            }
        }

        JsonArray playersJsonArray = tile.getJsonArray("players", null);

        List<Player> playersList = null;
        List<String> playerNames = new ArrayList<>();
        if (playersJsonArray != null) {
            playersList = playersJsonArray.getList();
            for (Player player : playersList) {
                playerNames.add(player.getPlayerName().toString());
            }
        }

        String treasure = tile.getString("treasure", null); // Set default value to null

        return new Tile(
                treasure,
                wallsArray,
                playerNames,
                location
        );
    }
}

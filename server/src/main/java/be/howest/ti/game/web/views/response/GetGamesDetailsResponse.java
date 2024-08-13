package be.howest.ti.game.web.views.response;

import java.util.List;
import java.util.Map;

public class GetGamesDetailsResponse extends ResponseWithHiddenStatus{
    private final Map<String, Object> gameInfo;
    private final List<Map<String, Object>> players;

    public GetGamesDetailsResponse(Map<String, Object> gameInfo, List<Map<String, Object>> players) {
        super(200);
        this.gameInfo = gameInfo;
        this.players = players;
    }

    public Map<String, Object> getDescription() {
        return gameInfo;
    }

    public List<Map<String, Object>> getPlayers() {
        return players;
    }
}

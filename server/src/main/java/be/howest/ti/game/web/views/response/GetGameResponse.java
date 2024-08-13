package be.howest.ti.game.web.views.response;

import java.util.List;
import java.util.Map;

public class GetGameResponse extends ResponseWithHiddenStatus{
    private final List<Map<String, Object>> games;
    public GetGameResponse(List<Map<String, Object>> games) {
        super(200);
        this.games = games;
    }

    public List<Map<String, Object>> getGames() {
        return games;
    }
}

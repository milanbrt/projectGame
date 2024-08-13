package be.howest.ti.game.web.views.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetGameDetailsResponse extends ResponseWithHiddenStatus {
    private final Map<String, Object> gameInfo;
    private final Map<String, Object> allPlayersInfo;
    private final List<List<Map<String, Object>>> mazeStructure;
    private final Map<String, Object> spareTile;
    public GetGameDetailsResponse(Map<String, Object> gameInfo, Map<String, Object> allPlayersInfo, List<List<Map<String, Object>>> mazeStructure, Map<String, Object> spareTile) {
        super(200);
        this.gameInfo = gameInfo;
        this.allPlayersInfo = allPlayersInfo;
        this.mazeStructure = mazeStructure;
        this.spareTile = spareTile;
    }

    public Map<String, Object> getDescription() {
        return gameInfo;
    }

    public Map<String, Object> getPlayers() {
        return allPlayersInfo;
    }

    public List<List<Map<String, Object>>> getMaze() {
        return mazeStructure;
    }

    public Map<String, Object> getSpareTile() {
        return spareTile;
    }

}

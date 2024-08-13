package be.howest.ti.game.web.views.response;

import java.util.Map;

public class GetPlayerDetailsResponse extends ResponseWithHiddenStatus {
    private final Map<String, Object> playerInfo;
    public GetPlayerDetailsResponse(Map<String, Object> playerInfo) {
        super(200);
        this.playerInfo = playerInfo;
    }


    public Map<String, Object> getPlayer(){
        return Map.of(
                "name", playerInfo.get("name").toString(),
                "location", playerInfo.get("location"),
                "treasures", playerInfo.get("treasures"),
                "state", playerInfo.get("state").toString(),
                "objective", playerInfo.get("objective").toString()
        );
    }
}

package be.howest.ti.game.web.views.response;

public class LeaveGameResponse extends ResponseWithHiddenStatus {
    private final String gameId;
    private final String playerName;
    public LeaveGameResponse(String gameId, String playerName) {
        super(200);
        this.gameId = gameId;
        this.playerName = playerName;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

}
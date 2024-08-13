package be.howest.ti.game.web.views.response;

public class JoinGameResponse extends ResponseWithHiddenStatus {
    private final String gameId;
    private final String playerName;
    private final String playerToken;
    public JoinGameResponse(String gameId, String playerName, String playerToken) {
        super(200);
        this.gameId = gameId;
        this.playerName = playerName;
        this.playerToken = playerToken;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerToken() {
        return playerToken;
    }

}

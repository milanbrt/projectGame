package be.howest.ti.game.web.tokens;

import be.howest.ti.game.web.LabyrinthUser;
import java.util.Base64;

public class FancyTokens implements TokenManager {

    private String key;

    public FancyTokens(String key) {
        this.key = key;
    }

    private String encrypt(String data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            result.append((char) (data.charAt(i) ^ key.charAt(i % key.length())));
        }
        return result.toString();
    }

    private String decrypt(String encryptedData) {
        return encrypt(encryptedData);
    }

    @Override
    public String createToken(LabyrinthUser user) {
        String plainToken = user.getGameId() + "_" + user.getPlayerName();
        String encryptedToken = encrypt(plainToken);
        String base64Token = Base64.getEncoder().encodeToString(encryptedToken.getBytes());
        return base64Token;
    }

    @Override
    public LabyrinthUser createUser(String base64Token) {
        String encryptedToken = new String(Base64.getDecoder().decode(base64Token));
        String decryptedToken = decrypt(encryptedToken);
        String[] parts = decryptedToken.split("_");
        return new LabyrinthUser(parts[0], parts[1]);
    }

    @Override
    public boolean isValidPlayerName(String playerName) {
        return playerName.matches("[a-zA-Z]+");
    }
}
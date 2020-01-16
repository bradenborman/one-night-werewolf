package borman.onenight.models;

public class Player {

    private String playerId;
    private String username;
    private String lobbyPlaying;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLobbyPlaying() {
        return lobbyPlaying;
    }

    public void setLobbyPlaying(String lobbyPlaying) {
        this.lobbyPlaying = lobbyPlaying;
    }
}

package borman.onenight.models;

public class RetrieveRollRequest {

    private String playerId;
    private String lobbyPlaying;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getLobbyPlaying() {
        return lobbyPlaying;
    }

    public void setLobbyPlaying(String lobbyPlaying) {
        this.lobbyPlaying = lobbyPlaying;
    }
}

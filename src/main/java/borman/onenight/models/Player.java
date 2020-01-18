package borman.onenight.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    @JsonProperty("playerId")
    private String playerId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("lobbyPlaying")
    private String lobbyPlaying;

    @JsonProperty("isReadyToStart")
    private boolean isReadyToStart;

    @JsonProperty("roleAssigned")
    private Role roleAssigned;

    public Player() { }

    public Player(String playerId, String username, String lobbyPlaying) {
        this.playerId = playerId;
        this.username = username;
        this.lobbyPlaying = lobbyPlaying;
    }

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

    public boolean isReadyToStart() {
        return isReadyToStart;
    }

    public void setReadyToStart(boolean readyToStart) {
        isReadyToStart = readyToStart;
    }

    public Role getRoleAssigned() {
        return roleAssigned;
    }

    public void setRoleAssigned(Role roleAssigned) {
        this.roleAssigned = roleAssigned;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId='" + playerId + '\'' +
                ", username='" + username + '\'' +
                ", lobbyPlaying='" + lobbyPlaying + '\'' +
                '}';
    }
}

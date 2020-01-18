package borman.onenight.models;

import java.util.List;

public class UsersPlayingResponse {

    private List<Player> playersInLobby;
    private String generatedPlayerId;

    public List<Player> getPlayersInLobby() {
        return playersInLobby;
    }

    public void setPlayersInLobby(List<Player> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    public String getGeneratedPlayerId() {
        return generatedPlayerId;
    }

    public void setGeneratedPlayerId(String generatedPlayerId) {
        this.generatedPlayerId = generatedPlayerId;
    }
}

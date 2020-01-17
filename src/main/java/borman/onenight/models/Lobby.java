package borman.onenight.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    @JsonProperty("lobbyId")
    private String lobbyId;

    @JsonProperty("playersInLobby")
    private List<Player> playersInLobby = new ArrayList<>();

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public List<Player> getPlayersInLobby() {
        return playersInLobby;
    }


    public void setPlayersInLobby(List<Player> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "lobbyId='" + lobbyId + '\'' +
                ", playersInLobby=" + playersInLobby +
                '}';
    }
}

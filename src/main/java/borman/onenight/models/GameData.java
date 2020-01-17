package borman.onenight.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GameData {

    @JsonProperty("lobbyList")
    private List<Lobby> lobbyList = new ArrayList<>();

    public List<Lobby> getLobbyList() {
        return lobbyList;
    }

    public void setLobbyList(List<Lobby> lobbyList) {
        this.lobbyList = lobbyList;
    }

    @Override
    public String toString() {
        return "GameData{" +
                "lobbyList=" + lobbyList +
                '}';
    }
}
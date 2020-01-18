package borman.onenight.models;

import java.util.List;

public class UsersPlayingResponse {

    private String lobbyId;

    private boolean isReadyToStartGame = false;
    private List<Player> playersInLobby;
    private String generatedPlayerId;


    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public boolean isReadyToStartGame() {
        return isReadyToStartGame;
    }

    public void setReadyToStartGame(boolean readyToStartGame) {
        isReadyToStartGame = readyToStartGame;
    }

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

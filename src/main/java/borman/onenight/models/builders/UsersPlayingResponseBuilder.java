package borman.onenight.models.builders;

import borman.onenight.models.Player;
import borman.onenight.models.UsersPlayingResponse;

import java.util.List;

public final class UsersPlayingResponseBuilder {
    private String lobbyId;
    private List<Player> playersInLobby;
    private String generatedPlayerId;

    private UsersPlayingResponseBuilder() {
    }

    public static UsersPlayingResponseBuilder anUsersPlayingResponse() {
        return new UsersPlayingResponseBuilder();
    }

    public UsersPlayingResponseBuilder withLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
        return this;
    }

    public UsersPlayingResponseBuilder withPlayersInLobby(List<Player> playersInLobby) {
        this.playersInLobby = playersInLobby;
        return this;
    }

    public UsersPlayingResponseBuilder withGeneratedPlayerId(String generatedPlayerId) {
        this.generatedPlayerId = generatedPlayerId;
        return this;
    }

    public UsersPlayingResponse build() {
        UsersPlayingResponse usersPlayingResponse = new UsersPlayingResponse();
        usersPlayingResponse.setLobbyId(lobbyId);
        usersPlayingResponse.setPlayersInLobby(playersInLobby);
        usersPlayingResponse.setGeneratedPlayerId(generatedPlayerId);
        return usersPlayingResponse;
    }

}
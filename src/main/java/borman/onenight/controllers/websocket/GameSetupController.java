package borman.onenight.controllers.websocket;

import borman.onenight.services.ReadyToStartService;
import borman.onenight.models.builders.UsersPlayingResponseBuilder;
import borman.onenight.services.UserService;
import borman.onenight.models.*;
import borman.onenight.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameSetupController {

    @Autowired
    LobbyService lobbyService;

    @Autowired
    UserService userService;

    @Autowired
    ReadyToStartService readyToStartService;

    @MessageMapping("/join-game")
    @SendTo("/one-night/users-playing")
    public synchronized UsersPlayingResponse start(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {

        String createUserID = userService.createUser(player);

        headerAccessor.getSessionAttributes().put("playerId", createUserID);
        headerAccessor.getSessionAttributes().put("lobbyId", player.getLobbyPlaying());

        List<Player> playersInLobby = lobbyService.getPlayersInLobby(player.getLobbyPlaying());
        return UsersPlayingResponseBuilder.anUsersPlayingResponse()
                .withGeneratedPlayerId(createUserID)
                .withLobbyId(player.getLobbyPlaying())
                .withPlayersInLobby(playersInLobby)
                .build();
    }

    @MessageMapping("/ready-to-start")
    @SendTo("/one-night/users-playing")
    public synchronized UsersPlayingResponse readyToStart(@Payload ReadyToStartRequest readyToStartRequest) {

        userService.updateReadyToPlay(readyToStartRequest.getPlayerId(), readyToStartRequest.getLobbyPlaying());

        Lobby lobby = new Lobby();
        lobby.setLobbyId(readyToStartRequest.getLobbyPlaying());
        lobby.setPlayersInLobby(lobbyService.getPlayersInLobby(readyToStartRequest.getLobbyPlaying()));

        boolean isReadyToStart = readyToStartService.isReadyToStart(lobby);

        UsersPlayingResponse response = UsersPlayingResponseBuilder.anUsersPlayingResponse()
                .withGeneratedPlayerId(readyToStartRequest.getPlayerId())
                .withLobbyId(readyToStartRequest.getLobbyPlaying())
                .withPlayersInLobby(lobby.getPlayersInLobby())
                .build();

        response.setReadyToStartGame(isReadyToStart);

        return response;
    }


    @MessageMapping("/drunk-action")
    @SendTo("/one-night/drunk-played")
    public synchronized DrunkActionResponse drunkAction(@Payload DrunkActionRequest drunkActionRequest) {
        return userService.exucuteDrunkAction(drunkActionRequest);
    }

}
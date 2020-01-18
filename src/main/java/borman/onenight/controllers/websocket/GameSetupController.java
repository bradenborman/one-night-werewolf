package borman.onenight.controllers.websocket;

import borman.onenight.ReadyToStartService;
import borman.onenight.utilities.RandomUtility;
import borman.onenight.models.*;
import borman.onenight.services.DataService;
import borman.onenight.services.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class GameSetupController {

    private static final Logger logger = LoggerFactory.getLogger(GameSetupController.class);

    @Autowired
    DataService dataService;

    @Autowired
    LobbyService lobbyService;

    @Autowired
    ReadyToStartService readyToStartService;

    @MessageMapping("/join-game")
    @SendTo("/one-night/users-playing")
    public synchronized UsersPlayingResponse start(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {

        GameData existingGameData = dataService.readJsonFile();

        player.setPlayerId(RandomUtility.createUserIdForSession(player.getUsername()));
        player.setReadyToStart(false);

        headerAccessor.getSessionAttributes().put("playerId", player.getPlayerId() + ":" + player.getLobbyPlaying());

        //get lobby where user is playing.
        Lobby lobbyUserIsPlaying = existingGameData.getLobbyList().stream()
                .filter(lob -> lob.getLobbyId().equals(player.getLobbyPlaying()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        lobbyUserIsPlaying.getPlayersInLobby().add(player);


        //Update with new data
        lobbyService.updateGameDataWithNewLobbyDetail(existingGameData, lobbyUserIsPlaying);
        dataService.writeDataToFile(existingGameData);

        //Map to response - players list and player ale
        UsersPlayingResponse response = new UsersPlayingResponse();
        response.setPlayersInLobby(lobbyUserIsPlaying.getPlayersInLobby());
        response.setGeneratedPlayerId(player.getPlayerId());

        return response;
    }

    @MessageMapping("/ready-to-start")
    @SendTo("/one-night/users-playing")
    public synchronized UsersPlayingResponse readyToStart(@Payload ReadyToStartRequest readyToStartRequest) {

        GameData existingGameData = dataService.readJsonFile();
        UsersPlayingResponse response = new UsersPlayingResponse();

        //get lobby where user is playing.
        Lobby lobbyUserIsPlaying = existingGameData.getLobbyList().stream()
                .filter(lob -> lob.getLobbyId().equals(readyToStartRequest.getLobbyPlaying()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        lobbyUserIsPlaying.getPlayersInLobby().stream()
                .filter(player -> player.getPlayerId().equals(readyToStartRequest.getPlayerId()))
                .findFirst()
                .ifPresent(player -> {
                    response.setGeneratedPlayerId(player.getPlayerId());
                    player.setReadyToStart(true);
                });

        response.setPlayersInLobby(lobbyUserIsPlaying.getPlayersInLobby());


        response.setReadyToStartGame(
            readyToStartService.isReadyToStart(lobbyUserIsPlaying)
        );


        //Update with new data
        lobbyService.updateGameDataWithNewLobbyDetail(existingGameData, lobbyUserIsPlaying);
        dataService.writeDataToFile(existingGameData);

        return response;
    }

}

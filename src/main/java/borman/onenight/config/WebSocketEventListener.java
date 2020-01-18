package borman.onenight.config;

import borman.onenight.models.GameData;
import borman.onenight.models.Lobby;
import borman.onenight.models.UsersPlayingResponse;
import borman.onenight.services.DataService;
import borman.onenight.services.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.stream.Collectors;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);


    @Autowired
    DataService dataService;

    @Autowired
    LobbyService lobbyService;


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        GameData existingGameData = dataService.readJsonFile();

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String playerIdAndLobby = (String) headerAccessor.getSessionAttributes().get("playerId");

        String[] x = playerIdAndLobby.split(":");
        String playerId = x[0];
        String lobby = x[1];

        Lobby lobbyUserWasPlaying = existingGameData.getLobbyList().stream()
                .filter(lob -> lob.getLobbyId().equals(lobby))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //RemoveUser
        lobbyUserWasPlaying.setPlayersInLobby(lobbyUserWasPlaying.getPlayersInLobby().stream().filter(player -> !player.getPlayerId().equals(playerId)).collect(Collectors.toList()));

        lobbyService.updateGameDataWithNewLobbyDetail(existingGameData, lobbyUserWasPlaying);

        //Update with new data
        dataService.writeDataToFile(existingGameData);


        //Map to response - players list and player ale
        UsersPlayingResponse response = new UsersPlayingResponse();
        response.setPlayersInLobby(lobbyUserWasPlaying.getPlayersInLobby());
        response.setGeneratedPlayerId(playerId);
        response.setLobbyId(lobbyUserWasPlaying.getLobbyId());



        messagingTemplate.convertAndSend("/one-night/users-playing", response);
        logger.info(playerId + " lost");
    }

}

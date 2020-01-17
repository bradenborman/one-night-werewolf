package borman.onenight.config;

import borman.onenight.models.GameData;
import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
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

import java.util.List;
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
        lobbyUserWasPlaying.getPlayersInLobby().stream().filter(player -> !player.getPlayerId().equals(playerId)).collect(Collectors.toList());

        //Remaining Users
       List<String> remainingUsers = lobbyUserWasPlaying.getPlayersInLobby().stream().map(Player::getUsername).collect(Collectors.toList());

        lobbyService.updateGameDataWithNewLobbyDetail(existingGameData, lobbyUserWasPlaying);

        //Update with new data
        dataService.writeDataToFile(existingGameData);

        messagingTemplate.convertAndSend("/one-night/users-playing", remainingUsers);
        logger.info(playerId + " lost");
    }

}

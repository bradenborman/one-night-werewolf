package borman.onenight.config;

import borman.onenight.models.Player;
import borman.onenight.models.UsersPlayingResponse;
import borman.onenight.models.builders.UsersPlayingResponseBuilder;
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

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

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
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String playerId = (String) headerAccessor.getSessionAttributes().get("playerId");
        String lobbyID = (String) headerAccessor.getSessionAttributes().get("lobbyId");

        //RemoveUser
        lobbyService.removeUserFromLobby(playerId, lobbyID);

        List<Player> playersInLobby = lobbyService.getPlayersInLobby(lobbyID);

        UsersPlayingResponse response = UsersPlayingResponseBuilder.anUsersPlayingResponse()
                .withGeneratedPlayerId(playerId)
                .withLobbyId(lobbyID)
                .withPlayersInLobby(playersInLobby)
                .build();

        messagingTemplate.convertAndSend("/one-night/users-playing", response);
    }

}

package borman.onenight.config;

import borman.onenight.data.PlayingList;
import borman.onenight.models.Player;
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
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        List<Player> updatedList = PlayingList.getAllPlaying().stream()
                .filter(player -> !player.getPlayerId().equals(username))
                .collect(Collectors.toList());

        PlayingList.setAllPlaying(updatedList);

        List<String> userNames = updatedList.stream().map(Player::getUsername).collect(Collectors.toList());

        messagingTemplate.convertAndSend("/one-night/users-playing", userNames);
        logger.info(username + " lost");
    }

}

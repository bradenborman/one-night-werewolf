package borman.onenight.controllers.websocket;

import borman.onenight.RandomService;
import borman.onenight.data.PlayingList;
import borman.onenight.models.GameData;
import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import borman.onenight.services.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GameSetupController {

    private static final Logger logger = LoggerFactory.getLogger(GameSetupController.class);

    @MessageMapping("/join-game")
    @SendTo("/one-night/users-playing")
    public List<String> start(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {
        player.setPlayerId(RandomService.createUserIdForSession(player.getUsername()));
        headerAccessor.getSessionAttributes().put("username", player.getPlayerId());
        List<Player> allPlaying = PlayingList.getAllPlaying();
        allPlaying.add(player);
        List<String> userNames = allPlaying.stream().map(Player::getUsername).collect(Collectors.toList());
        return userNames;
    }

}

package borman.onenight.controllers.websocket;

import borman.onenight.data.PlayingList;
import borman.onenight.models.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GameSetupController {

    @MessageMapping("/join-game")
    @SendTo("/one-night/users-playing")
    public List<String> start(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {

        //todo gen playerId and set in session
        headerAccessor.getSessionAttributes().put("username", player.getUsername());

        List<Player> allPlaying = PlayingList.getAllPlaying();
        allPlaying.add(player);

        List<String> userNames = allPlaying.stream().map(Player::getUsername).collect(Collectors.toList());

        return userNames;
    }

}

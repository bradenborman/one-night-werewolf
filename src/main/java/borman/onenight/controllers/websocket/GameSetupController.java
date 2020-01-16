package borman.onenight.controllers.websocket;

import borman.onenight.data.PlayingList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameSetupController {


    @MessageMapping("/join-game")
    @SendTo("/one-night/users-playing")
    public List<String> start(@Payload String input, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", input);
        List<String> allPlaying = PlayingList.getAllPlaying();
        allPlaying.add(input);
        return allPlaying;
    }


}

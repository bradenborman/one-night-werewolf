package borman.onenight.controllers.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameSetupController {

    @MessageMapping("/join-game")
    @SendTo("/one-night/users-playing")
    public String start(String input) {
        System.out.println(input);
        return "test";
    }

}

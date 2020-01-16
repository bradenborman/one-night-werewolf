package borman.onenight.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    //Simulates playerslist
    private List<String> allUsersPlaying = new ArrayList<>();


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/lobby/{lobby}")
    public String lobby(Model model, @PathVariable("lobby") String lobby, @RequestParam("playerName") String playerName)
    {

        allUsersPlaying.add(playerName);

        //Let already in lobby players know new player
        messagingTemplate.convertAndSend("/one-night/users-playing", allUsersPlaying);

        model.addAttribute("usersPlaying", allUsersPlaying);

        return "lobby";

    }

}
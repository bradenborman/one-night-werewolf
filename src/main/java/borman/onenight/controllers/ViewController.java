package borman.onenight.controllers;

import borman.onenight.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ViewController {


    @Autowired
    LobbyService lobbyService;

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/lobby/{lobby}")
    public String lobby(Model model, @PathVariable("lobby") String lobbyId, @RequestParam("playerName") String playerName) {
        if (lobbyService.doesLobbyExist(lobbyId)) {
            model.addAttribute("lobby", lobbyId);
            model.addAttribute("playerName", playerName);
            return "lobby";
        }
        return "redirect:/";
    }

}
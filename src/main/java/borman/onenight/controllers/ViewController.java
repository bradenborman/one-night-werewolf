package borman.onenight.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ViewController {


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/lobby/{lobby}")
    public String lobby(Model model, @PathVariable("lobby") String lobby, @RequestParam("playerName") String playerName)
    {
        model.addAttribute("playerName", playerName);
        return "lobby";
    }

}
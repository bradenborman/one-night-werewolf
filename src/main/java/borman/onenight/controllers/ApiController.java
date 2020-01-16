package borman.onenight.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @PostMapping("/startNewLobby")
    public String startNewLobby() {
        System.out.println("Starting new lobby");
        return "234";
    }

}

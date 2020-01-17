package borman.onenight.controllers;

import borman.onenight.RandomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @PostMapping("/startNewLobby")
    public String startNewLobby() {
        return RandomService.createRandomLobbyCode();
    }

}

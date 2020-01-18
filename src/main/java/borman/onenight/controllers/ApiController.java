package borman.onenight.controllers;

import borman.onenight.models.RetrieveRollRequest;
import borman.onenight.models.RetrieveRollResponse;
import borman.onenight.services.RetrieveRollService;
import borman.onenight.utilities.RandomUtility;
import borman.onenight.models.GameData;
import borman.onenight.models.Lobby;
import borman.onenight.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    DataService dataService;

    @Autowired
    RetrieveRollService retrieveRollService;

    @PostMapping("/startNewLobby")
    public String startNewLobby() {
        GameData existingGameData = dataService.readJsonFile();
        String lobbyCreated = RandomUtility.createRandomLobbyCode();
        Lobby lobby = new Lobby();
        lobby.setLobbyId(lobbyCreated);
        existingGameData.getLobbyList().add(lobby);
        dataService.writeDataToFile(existingGameData);
        return lobbyCreated;
    }


    @PostMapping("/retrieve-roll")
    public RetrieveRollResponse retrieveRoll(@RequestBody RetrieveRollRequest request) {
        GameData existingGameData = dataService.readJsonFile();
        return retrieveRollService.getUserRoll(request, existingGameData);
    }


}

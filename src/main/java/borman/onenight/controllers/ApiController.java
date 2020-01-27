package borman.onenight.controllers;

import borman.onenight.models.RetrieveRollRequest;
import borman.onenight.models.RetrieveRollResponse;
import borman.onenight.models.RobbedResponse;
import borman.onenight.models.SwapCardsRequest;
import borman.onenight.services.LobbyService;
import borman.onenight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    @Autowired
    LobbyService lobbyService;

    @Autowired
    UserService userService;

    @PostMapping("/startNewLobby")
    public String startNewLobby() {
        return lobbyService.createLobby();
    }

    @PostMapping("/retrieve-roll")
    public RetrieveRollResponse retrieveRoll(@RequestBody RetrieveRollRequest request) {
        return userService.getUsersRollById(request.getPlayerId(), request.getLobbyPlaying());
    }

    @GetMapping("/peek/{userId}/{lobbyId}")
    public ResponseEntity<String> peek(@PathVariable("userId") String userId, @PathVariable("lobbyId") String lobbyId) {
        return ResponseEntity.ok(
                userService.peekRollById(userId, lobbyId)
        );
    }

    @GetMapping("/steal/{otherplayer}/{userId}/{lobbyId}")
    public ResponseEntity<RobbedResponse> steal(@PathVariable("otherplayer") String otherplayer, @PathVariable("userId") String userId, @PathVariable("lobbyId") String lobbyId) {
        return ResponseEntity.ok(
                userService.swapUsersRoleRobber(otherplayer, userId, lobbyId)
        );
    }

    @PostMapping("/swap-cards")
    public ResponseEntity<String> swapCards(@RequestBody SwapCardsRequest request) {
        System.out.println(String.format("SWAPING CARDS REQUEST: %s | %s ", request.getTroubleMakerId_ONE(), request.getTroubleMakerId_TWO()));
        userService.swapUsersRoleTroubleMaker(request.getTroubleMakerId_ONE(), request.getTroubleMakerId_TWO());
        return ResponseEntity.ok("DONE");
    }



}
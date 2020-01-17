package borman.onenight.controllers.websocket;

import borman.onenight.RandomService;
import borman.onenight.models.GameData;
import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import borman.onenight.services.DataService;
import borman.onenight.services.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GameSetupController {

    private static final Logger logger = LoggerFactory.getLogger(GameSetupController.class);

    @Autowired
    DataService dataService;

    @Autowired
    LobbyService lobbyService;


    @MessageMapping("/join-game")
    @SendTo("/one-night/users-playing")
    public List<String> start(@Payload Player player, SimpMessageHeaderAccessor headerAccessor) {

        GameData existingGameData = dataService.readJsonFile();

        player.setPlayerId(RandomService.createUserIdForSession(player.getUsername()));

        headerAccessor.getSessionAttributes().put("playerId", player.getPlayerId() + ":" + player.getLobbyPlaying());

        //get lobby where user is playing.
        Lobby lobbyUserIsPlaying = existingGameData.getLobbyList().stream()
                .filter(lob -> lob.getLobbyId().equals(player.getLobbyPlaying()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        lobbyUserIsPlaying.getPlayersInLobby().add(player);

        lobbyService.updateGameDataWithNewLobbyDetail(existingGameData, lobbyUserIsPlaying);

        //Update with new data
        dataService.writeDataToFile(existingGameData);

        return lobbyUserIsPlaying.getPlayersInLobby().stream().map(Player::getUsername).collect(Collectors.toList());
    }

}

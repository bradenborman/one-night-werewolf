package borman.onenight.services;

import borman.onenight.models.GameData;
import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LobbyService {

    @Autowired
    DataService dataService;


    public boolean addUserToLobby(String lobbyId, String playerName) {
        GameData existingGameData = dataService.readJsonFile();

        Optional<Lobby> lobby = existingGameData.getLobbyList().stream()
                .filter(lo -> lo.getLobbyId().equals(lobbyId))
                .findFirst();

        if (!lobby.isPresent())
            return false;

        lobby.get().getPlayersInLobby().add(new Player("", playerName, lobbyId));
        return true;
    }


}

package borman.onenight.services;

import borman.onenight.models.GameData;
import borman.onenight.models.Lobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LobbyService {

    @Autowired
    DataService dataService;


    public boolean doesLobbyExist(String lobbyId) {

        GameData existingGameData = dataService.readJsonFile();

        Lobby lobby = existingGameData.getLobbyList().stream()
                .filter(lo -> lo.getLobbyId().equals(lobbyId))
                .findFirst()
                .orElse(new Lobby());

        return lobby.getLobbyId() != null;

    }


    public void updateGameDataWithNewLobbyDetail(GameData gameData, Lobby lobbyChanged) {

        gameData.getLobbyList()
                .removeIf(lobby -> lobby.getLobbyId().equals(lobbyChanged.getLobbyId()));

        //Add back lobby if players are still in it. else will delete
        if (lobbyChanged.getPlayersInLobby().size() > 0)
            gameData.getLobbyList().add(lobbyChanged);

    }


}

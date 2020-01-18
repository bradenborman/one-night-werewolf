package borman.onenight;

import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import org.springframework.stereotype.Service;


@Service
public class ReadyToStartService {


    /*
    If there are at least 5 people and they are all ready
     */
    public boolean isReadyToStart(Lobby lobby) {
        return lobby.getPlayersInLobby().stream()
                .allMatch(Player::isReadyToStart) && lobby.getPlayersInLobby().size() > 1;
    }

    //TODO update lobby with new field and set true.

}

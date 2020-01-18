package borman.onenight;

import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import borman.onenight.services.DetermineRolesService;
import org.springframework.stereotype.Service;


@Service
public class ReadyToStartService {


    /*
    If there are at least 5 people and they are all ready
     */
    public boolean isReadyToStart(Lobby lobby) {
        boolean isReady = lobby.getPlayersInLobby().stream()
                .allMatch(Player::isReadyToStart) && lobby.getPlayersInLobby().size() > 1;

        if(isReady)
        {
            DetermineRolesService determineRolesService = new DetermineRolesService();
            determineRolesService.createRolesForPlayersAndDefineMiddleCards(lobby);
        }

        return isReady;
    }


}

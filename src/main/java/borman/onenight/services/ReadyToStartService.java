package borman.onenight.services;

import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import borman.onenight.services.DetermineRolesService;
import borman.onenight.services.LobbyService;
import borman.onenight.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReadyToStartService {

    @Autowired
    UserService userService;

    @Autowired
    LobbyService lobbyService;

    /*
    If there are at least 5 people and they are all ready
     */
    public boolean isReadyToStart(Lobby lobby) {
        boolean isReady = lobby.getPlayersInLobby().stream()
                .allMatch(Player::isReadyToStart) && lobby.getPlayersInLobby().size() >= 3;

        if(isReady)
        {
            DetermineRolesService determineRolesService = new DetermineRolesService();
            determineRolesService.createRolesForPlayersAndDefineMiddleCards(lobby);
            lobbyService.lockLobby(lobby.getLobbyId());
            lobbyService.updateCommunityCards(lobby.getMiddleCards(), lobby.getLobbyId());
            userService.updateSetRoles(lobby);
        }

        return isReady;
    }


}

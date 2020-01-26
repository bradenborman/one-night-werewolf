package borman.onenight.services;

import borman.onenight.daos.UserDao;
import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import borman.onenight.models.RetrieveRollResponse;
import borman.onenight.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    LobbyService lobbyService;

    public String createUser(Player player) {
        return userDao.createUser(player);
    }

    public void updateReadyToPlay(String playerId, String lobbyPlaying) {
        userDao.updateReadyToPlay(playerId, lobbyPlaying);
    }

    public void updateSetRoles(Lobby lobby) {
        lobby.getPlayersInLobby().forEach(player -> {
            userDao.updatePlayRole(player.getRoleAssigned(), player.getPlayerId());
        });
    }

    public RetrieveRollResponse getUsersRollById(String playerId, String lobbyId) {
        Player player = userDao.getPlayer(playerId);

        Role roleAssigned = player.getRoleAssigned();

        RetrieveRollResponse response = new RetrieveRollResponse();
        List<Role> communityCards = lobbyService.getCommunityCards(lobbyId);

        response.setMiddleCards(
                communityCards.stream().map(Role::getImgSrc).collect(Collectors.toList())
        );
        response.setRoleName(roleAssigned.name());
        response.setImgSrc(roleAssigned.getImgSrc());
        response.setDisplayName(roleAssigned.getDisplayName());

        return response;
    }

    public String peekRollById(String userId, String lobbyId) {
      try {
          return getUsersRollById(userId, lobbyId).getImgSrc();
      }catch (Exception e) {
          return "No Role";
      }
    }
}
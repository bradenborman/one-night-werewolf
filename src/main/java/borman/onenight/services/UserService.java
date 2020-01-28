package borman.onenight.services;

import borman.onenight.daos.UserDao;
import borman.onenight.models.*;
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
        } catch (Exception e) {
            return "No Role";
        }
    }

    public RobbedResponse swapUsersRoleRobber(String otherplayer, String userId, String lobbyId) {
        RobbedResponse response = new RobbedResponse();

        Player originalRobber = userDao.getPlayer(userId);
        Player roleToChangeTo = userDao.getPlayer(otherplayer);

        userDao.updatePlayRole(originalRobber.getRoleAssigned(), otherplayer);
        userDao.updatePlayRole(roleToChangeTo.getRoleAssigned(), userId);

        response.setNewRoleForRobber(roleToChangeTo.getRoleAssigned().getDisplayName());
        response.setImgSrc(roleToChangeTo.getRoleAssigned().getImgSrc());

        return response;
    }


    public void swapUsersRoleTroubleMaker(String troubleMakerId_one, String troubleMakerId_two) {
        Player one = userDao.getPlayer(troubleMakerId_one);
        Player two = userDao.getPlayer(troubleMakerId_two);
        userDao.updatePlayRole(two.getRoleAssigned(), troubleMakerId_one);
        userDao.updatePlayRole(one.getRoleAssigned(), troubleMakerId_two);
    }

    public DrunkActionResponse exucuteDrunkAction(DrunkActionRequest drunkActionRequest) {

        Role oldRole = userDao.getPlayer(drunkActionRequest.getUserId()).getRoleAssigned();

        //Get new role for data
        DrunkActionResponse response = new DrunkActionResponse();
        Role role = Role.valueOf(lobbyService.getCommunityCardByPosition(drunkActionRequest.getCardSelectedPosition(), drunkActionRequest.getLobbyId()));
        response.setImgSrc_new(role.getImgSrc());
        response.setImgSrc_old(oldRole.getImgSrc());
        response.setPositionSwapped(drunkActionRequest.getCardSelectedPosition());
        response.setLobbyId(drunkActionRequest.getLobbyId());
        response.setPlayerId(drunkActionRequest.getUserId());

        //update database for users card and drunk in community.
        lobbyService.updateCommunityCard(drunkActionRequest.getCardSelectedPosition(), Role.DRUNK.name(), drunkActionRequest.getLobbyId());
        userDao.updatePlayRole(role, drunkActionRequest.getUserId());

        return response;
    }
}
package borman.onenight.services;

import borman.onenight.daos.LobbyDao;
import borman.onenight.models.Player;
import borman.onenight.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class LobbyService {

    @Autowired
    LobbyDao lobbyDao;

    public boolean isGameLocked(String lobbyId) {
        return lobbyDao.isGameLocked(lobbyId);
    }


    public String createLobby() {
        return lobbyDao.createNewLobby();
    }

    public boolean doesLobbyExist(String lobbyCreated) {
        return lobbyDao.checkForLobby(lobbyCreated);
    }

    public List<Player> getPlayersInLobby(String lobbyPlaying) {
        return lobbyDao.getPlayersInLobby(lobbyPlaying);
    }

    public void removeUserFromLobby(String playerId, String lobbyID) {
        lobbyDao.removeUserFromLobby(playerId, lobbyID);
    }

    public void lockLobby(String lobbyId) {
        lobbyDao.lockLobby(lobbyId);
    }

    public void updateCommunityCards(List<Role> middleCards, String lobbyId) {
        Role left = middleCards.get(0);
        Role middle = middleCards.get(1);
        Role right = middleCards.get(2);

        lobbyDao.updateLeftCommunityCard(left.name(), lobbyId);
        lobbyDao.updateMiddleCommunityCard(middle.name(), lobbyId);
        lobbyDao.updateRightCommunityCard(right.name(), lobbyId);

    }

    public List<Role> getCommunityCards(String lobbyId) {

        Role left = Role.valueOf(lobbyDao.fetchLeftCommunityCard(lobbyId));
        Role middle = Role.valueOf(lobbyDao.fetchMiddleCommunityCard(lobbyId));
        Role right = Role.valueOf(lobbyDao.fetchRightCommunityCard(lobbyId));

        return Arrays.asList(left, middle, right);
    }

    public boolean isRoomFromMe(String lobbyId) {
        int MAX_SIZE = Role.values().length - 3;
        return lobbyDao.countUsersInLobby(lobbyId) < MAX_SIZE;
    }

    String getCommunityCardByPosition(String position, String lobbyId) {
        switch (position) {
            case "left":
                return lobbyDao.fetchLeftCommunityCard(lobbyId);
            case "middle":
                return lobbyDao.fetchMiddleCommunityCard(lobbyId);
            case "right":
                return lobbyDao.fetchRightCommunityCard(lobbyId);
            default:
                return "";
        }
    }

    void updateCommunityCard(String cardSelectedPosition, String name, String lobbyId) {
        switch (cardSelectedPosition) {

            case "left":
                lobbyDao.updateLeftCommunityCard(name, lobbyId);
                break;

            case "middle":
                lobbyDao.updateMiddleCommunityCard(name, lobbyId);
                break;

            case "right":
                lobbyDao.updateRightCommunityCard(name, lobbyId);
                break;

            default:
                break;
        }
    }
}
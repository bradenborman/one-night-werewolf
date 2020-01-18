package borman.onenight.services;

import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import borman.onenight.models.Role;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class DetermineRolesService {

    public void createRolesForPlayersAndDefineMiddleCards(Lobby lobby) {

        List<Role> rolesToUse = determineRolesInGame(lobby.getPlayersInLobby().size());

        lobby.setMiddleCards(rolesToUse.subList((rolesToUse.size() - 3), rolesToUse.size()));

        List<Player> players = lobby.getPlayersInLobby();

        for (int x = 0; x < players.size(); x++) {
            players.get(x).setRoleAssigned(rolesToUse.get(x));
        }


        System.out.println(lobby.toString());
    }

    private List<Role> determineRolesInGame(int sizeOfLobby) {

        final int AMOUNT_CARDS_IN_PLAY = sizeOfLobby + 3;//cards in the middle

        List<Role> rolesInPlay = Arrays.stream(Role.values())
                .limit(AMOUNT_CARDS_IN_PLAY)
                .collect(Collectors.toList());

        Collections.shuffle(rolesInPlay);

        return rolesInPlay;
    }

}

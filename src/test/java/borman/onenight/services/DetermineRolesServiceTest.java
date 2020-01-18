package borman.onenight.services;

import borman.onenight.models.Lobby;
import borman.onenight.models.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class DetermineRolesServiceTest {


    @Test
    public void determineRolesInGameTest() {

        Player player1 = new Player();
        player1.setUsername("Player1");
        player1.setReadyToStart(true);
        player1.setPlayerId("Player14366");
        player1.setLobbyPlaying("1");

        Player player2 = new Player();
        player2.setUsername("Player2");
        player2.setReadyToStart(true);
        player2.setPlayerId("Player24366");
        player2.setLobbyPlaying("1");

        Player player3 = new Player();
        player3.setUsername("player3");
        player3.setReadyToStart(true);
        player3.setPlayerId("player34366");
        player3.setLobbyPlaying("1");

        Player player4 = new Player();
        player4.setUsername("player4");
        player4.setReadyToStart(true);
        player4.setPlayerId("player44366");
        player4.setLobbyPlaying("1");

        Lobby lobby = new Lobby();
        lobby.setLobbyId("1");
        lobby.setPlayersInLobby(Arrays.asList(player1, player2, player3, player4));

        DetermineRolesService determineRolesService = new DetermineRolesService();

        determineRolesService.createRolesForPlayersAndDefineMiddleCards(lobby);

    }

}
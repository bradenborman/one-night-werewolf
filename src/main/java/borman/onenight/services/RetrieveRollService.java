package borman.onenight.services;

import borman.onenight.models.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RetrieveRollService {



    public RetrieveRollResponse getUserRoll(RetrieveRollRequest request, GameData existingGameData) {

        RetrieveRollResponse retrieveRollResponse = new RetrieveRollResponse();

        //get lobby where user is playing.
        Lobby lobbyUserIsPlaying = existingGameData.getLobbyList().stream()
                .filter(lob -> lob.getLobbyId().equals(request.getLobbyPlaying()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        lobbyUserIsPlaying.getPlayersInLobby().stream()
                .filter(player -> player.getPlayerId().equals(request.getPlayerId()))
                .findFirst()
                .ifPresent(player -> {
                    Role usersRole = player.getRoleAssigned();
                    retrieveRollResponse.setDisplayName(usersRole.getDisplayName());
                    retrieveRollResponse.setImgSrc(usersRole.getImgSrc());
                    retrieveRollResponse.setRoleName(usersRole.name());
                });


        retrieveRollResponse.setMiddleCards(
                lobbyUserIsPlaying.getMiddleCards().stream().map(Role::getImgSrc).collect(Collectors.toList())
        );

        return retrieveRollResponse;
    }

}

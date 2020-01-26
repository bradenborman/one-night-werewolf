package borman.onenight.daos;

import borman.onenight.mappers.PlayerMapper;
import borman.onenight.models.Player;
import borman.onenight.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDao {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public String createUser(Player player) {

        Map<String, Object> parameters = new HashMap<>();

        SimpleJdbcInsert insertLobby = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("players")
                .usingGeneratedKeyColumns("player_id");

        parameters.put("username", player.getUsername());
        parameters.put("is_ready_up", false);
        parameters.put("lobby_playing", player.getLobbyPlaying());

        Number x = insertLobby.executeAndReturnKey(parameters);
        return String.valueOf(x);
    }


    public void updateReadyToPlay(String playerId, String lobbyPlaying) {
        String sql = "UPDATE players SET is_ready_up = true WHERE player_id = '" + playerId + "' AND lobby_playing = '" + lobbyPlaying + "';";
        jdbcTemplate.update(sql);
    }

    public void updatePlayRole(Role roleAssigned, String playerId) {
        String sql = "UPDATE players SET role_assigned = '" + roleAssigned.name() + "' WHERE player_id = '" + playerId + "';";
        jdbcTemplate.update(sql);
    }

    public Player getPlayer(String playerId) {
        String SQL = "SELECT * FROM players WHERE player_id = '" + playerId + "'";
        return jdbcTemplate.queryForObject(SQL, new PlayerMapper());
    }
}
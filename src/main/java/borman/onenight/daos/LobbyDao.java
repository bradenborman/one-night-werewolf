package borman.onenight.daos;

import borman.onenight.mappers.PlayerMapper;
import borman.onenight.models.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LobbyDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LobbyDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public String createNewLobby() {

        SimpleJdbcInsert insertLobby = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("lobbies")
                .usingGeneratedKeyColumns("lobby_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("isLocked", false);

        Number x = insertLobby.executeAndReturnKey(parameters);
        return String.valueOf(x);

    }

    public boolean checkForLobby(String lobbyId) {
        String SQL = "SELECT count(*) FROM lobbies WHERE lobby_id = '" + lobbyId + "';";
        Integer x = jdbcTemplate.queryForObject(SQL, Integer.class);
        return x != null && x > 0;
    }

    public boolean isGameLocked(String lobbyId) {
        String SQL = "SELECT isLocked FROM lobbies WHERE lobby_id = '" + lobbyId + "'";
        Integer x = jdbcTemplate.queryForObject(SQL, Integer.class);
        return x != null && x > 0;
    }


    public List<Player> getPlayersInLobby(String lobbyPlaying) {
        String SQL = "SELECT * FROM players WHERE lobby_playing = '" + lobbyPlaying + "'";
        return jdbcTemplate.query(SQL, new PlayerMapper());
    }

    public void removeUserFromLobby(String playerId, String lobbyID) {
        String SQL = "DELETE FROM players WHERE player_id = '" + playerId + "' AND lobby_playing = '" + lobbyID + "';";
        jdbcTemplate.update(SQL);
    }

    public void lockLobby(String lobbyId) {
        String sql = "UPDATE lobbies SET isLocked = true WHERE lobby_id = '" + lobbyId + "';";
        jdbcTemplate.update(sql);
    }


    public void updateLeftCommunityCard(String name, String lobbyId) {
        String sql = "UPDATE lobbies SET left_community_card = '" + name + "' WHERE lobby_id = '" + lobbyId + "';";
        jdbcTemplate.update(sql);
    }

    public void updateMiddleCommunityCard(String name, String lobbyId) {
        String sql = "UPDATE lobbies SET middle_community_card = '" + name + "' WHERE lobby_id = '" + lobbyId + "';";
        jdbcTemplate.update(sql);
    }

    public void updateRightCommunityCard(String name, String lobbyId) {
        String sql = "UPDATE lobbies SET right_community_card = '" + name + "' WHERE lobby_id = '" + lobbyId + "';";
        jdbcTemplate.update(sql);
    }

    public String fetchLeftCommunityCard(String lobbyId) {
        String sql = "SELECT left_community_card FROM lobbies WHERE lobby_id = '" + lobbyId + "'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public String fetchMiddleCommunityCard(String lobbyId) {
        String sql = "SELECT middle_community_card FROM lobbies WHERE lobby_id = '" + lobbyId + "'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public String fetchRightCommunityCard(String lobbyId) {
        String sql = "SELECT right_community_card FROM lobbies WHERE lobby_id = '" + lobbyId + "'";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public int countUsersInLobby(String lobbyId) {
        return getPlayersInLobby(lobbyId).size();
    }
}
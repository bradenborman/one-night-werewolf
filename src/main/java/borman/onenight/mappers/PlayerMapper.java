package borman.onenight.mappers;

import borman.onenight.models.Player;
import borman.onenight.models.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs, int rowNumber) throws SQLException {

        Player player = new Player();
        player.setPlayerId(rs.getString("player_id"));
        player.setUsername(rs.getString("username"));
        player.setReadyToStart(rs.getBoolean("is_ready_up"));
        player.setLobbyPlaying(rs.getString("lobby_playing"));

        try {
            Role role = Role.valueOf(rs.getString("role_assigned"));
            player.setRoleAssigned(role);
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return player;
    }

}
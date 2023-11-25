package com.rewardapp.backend.repositories;

import com.rewardapp.backend.models.UserModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class UserRepository {
    private static final String SQL_CREATE =
            "INSERT INTO users (username, password, email) " +
            "VALUES (?, ?, ?);";

    private static final String SQL_GET_BY_ID =
            "SELECT * FROM users " +
            "WHERE id = ?;";

    private static final String SQL_GET_BY_USERNAME =
            "SELECT * FROM users " +
            "WHERE username = ?;";
    private final JdbcTemplate jdbc;
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public UserModel register(UserModel user){
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            return ps;
        }, holder);

        Integer id = (Integer) holder.getKeys().get("id");
        user.setId(id);

        return user;
    }

    public UserModel find_by_id(Integer id) {
        return jdbc.queryForObject(SQL_GET_BY_ID, this::map_row_to_user, id);
    }

    public UserModel find_by_username(String username) {
        return jdbc.queryForObject(SQL_GET_BY_USERNAME, this::map_row_to_user, username);
    }

    private UserModel map_row_to_user(ResultSet resultSet, int rowNum) throws SQLException {
        UserModel user = new UserModel();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}

package com.rewardapp.backend.repositories;

import com.rewardapp.backend.exceptions.AuthException;
import com.rewardapp.backend.models.User;
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
            "WHERE id = ?";

    private final JdbcTemplate jdbc;
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    public Integer register(String username, String email, String password) throws AuthException {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            return ps;
        }, holder);
        return (Integer) holder.getKeys().get("id");
    }

    public User find_by_id(Integer id) {
        return jdbc.queryForObject(SQL_GET_BY_ID, this::map_row_to_user, id);
    }

    private User map_row_to_user(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}

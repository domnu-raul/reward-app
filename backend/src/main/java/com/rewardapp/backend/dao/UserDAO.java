package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.User;
import com.rewardapp.backend.models.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Component
@RequiredArgsConstructor
public class UserDAO {
    private static final RowMapper<User> rowMapper = RowMappers.USER_ROW_MAPPER;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> list() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public User setVerified(Long id) {
        String sql = "UPDATE users SET verified = true WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        return new User((Long) keys.get("id"), (String) keys.get("username"),
                (String) keys.get("email"), (Boolean) keys.get("verified"),
                keys.get("register_date").toString(),
                User.UserType.valueOf((String) keys.get("type")));
    }

    public User getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public User save(User user) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        return new User((Long) keys.get("id"), (String) keys.get("username"),
                (String) keys.get("email"), (Boolean) keys.get("verified"),
                keys.get("register_date").toString(),
                User.UserType.valueOf((String) keys.get("type")));
    }

    public User updateUser(Long userId, UserCredentials userCredentials) {
        List<String> setStatements = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (userCredentials.username() != null) {
            setStatements.add("username = :username");
            params.put("username", userCredentials.username());
        }
        if (userCredentials.email() != null) {
            setStatements.add("email = :email");
            params.put("email", userCredentials.email());
        }

        if (!setStatements.isEmpty()) {
            String sql = "UPDATE users SET " + String.join(", ", setStatements) +
                    " WHERE id = :id";
            params.put("id", userId);

            namedParameterJdbcTemplate.update(sql, params);
        }

        if (userCredentials.password() != null) {
            String salt = BCrypt.gensalt();
            String hashed_password = BCrypt.hashpw(userCredentials.password(), salt);

            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE internal_users SET password = ? WHERE id = ?");
                ps.setString(1, hashed_password);
                ps.setLong(2, userId);
                return ps;
            });
        }

        return this.getUserById(userId);
    }

    public Integer deleteUnverifiedUsersFrom(List<Long> ids) {
        String sql = "DELETE FROM users WHERE verified = false AND id IN (:ids)";

        Map<String, Object> params = Collections.singletonMap("ids", ids);

        return namedParameterJdbcTemplate.update(sql, params);
    }
}

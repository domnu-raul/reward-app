package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {
    private static final RowMapper<User> rowMapper = RowMappers.userMapper;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

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
        return new User(
                (Long) keys.get("id"),
                (String) keys.get("username"),
                (String) keys.get("email"),
                (Boolean) keys.get("verified"),
                keys.get("register_date").toString(),
                User.UserType.valueOf((String) keys.get("type"))
        );
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

        return new User(
                (Long) keys.get("id"),
                (String) keys.get("username"),
                (String) keys.get("email"),
                (Boolean) keys.get("verified"),
                keys.get("register_date").toString(),
                User.UserType.valueOf((String) keys.get("type"))
        );
    }

    public Integer deleteUnverifiedUsersFrom(List<Long> ids) {
        String sql = "DELETE FROM users WHERE verified = false AND id IN (:ids)";

        Map<String, Object> params = Collections.singletonMap("ids", ids);

        return namedParameterJdbcTemplate.update(sql, params);
    }
}

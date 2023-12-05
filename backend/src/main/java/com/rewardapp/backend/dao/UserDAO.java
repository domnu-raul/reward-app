package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {
    private static final RowMapper<User> rowMapper = (rs, rownum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setVerified(rs.getBoolean("verified"));
        user.setRegister_date(rs.getDate("register_date"));
        user.setType(User.UserType.valueOf(rs.getString("user_type")));

        return user;
    };
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<User> list() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Boolean setVerified(Long id) {
        String sql = "UPDATE users SET verified = true WHERE id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }

    public User getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users where username = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public Boolean save(User user) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getEmail()) == 1;
    }

    public Integer deleteUnverifiedUsersFrom(List<Long> ids) {
        String sql = "DELETE FROM users WHERE verified = false AND id IN (:ids)";

        Map<String, Object> params = Collections.singletonMap("ids", ids);

        return namedParameterJdbcTemplate.update(sql, params);
    }
}

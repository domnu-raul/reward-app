package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.UserModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {
    private static final RowMapper<UserModel> rowMapper = (rs, rownum) -> new UserModel(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getBoolean("verified"),
            rs.getDate("register_date"),
            UserModel.UserType.valueOf(rs.getString("type"))
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public UserDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserModel> list() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public UserModel setVerified(Long id) {
        String sql = "UPDATE users SET verified = true WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        UserModel userModel = new UserModel(
                (Long) keys.get("id"),
                (String) keys.get("username"),
                (String) keys.get("email"),
                (Boolean) keys.get("verified"),
                (Date) keys.get("register_date"),
                (UserModel.UserType) keys.get("type")
        );

        return userModel;
    }

    public UserModel getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public UserModel getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public UserModel save(UserModel userModel) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userModel.getUsername());
            ps.setString(2, userModel.getEmail());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        return new UserModel(
                (Long) keys.get("id"),
                (String) keys.get("username"),
                (String) keys.get("email"),
                (Boolean) keys.get("verified"),
                (Date) keys.get("register_date"),
                (UserModel.UserType) keys.get("type")
        );
    }

    public Integer deleteUnverifiedUsersFrom(List<Long> ids) {
        String sql = "DELETE FROM users WHERE verified = false AND id IN (:ids)";

        Map<String, Object> params = Collections.singletonMap("ids", ids);

        return namedParameterJdbcTemplate.update(sql, params);
    }
}

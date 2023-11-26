package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.InternalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InternalUserJdbcDAO implements DAO<InternalUser> {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(InternalUserJdbcDAO.class);

    private static final RowMapper<InternalUser> rowMapper = (rs, rowNum) -> {
        InternalUser user = new InternalUser();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setVerified(rs.getBoolean("verified"));
        user.setRegister_date(rs.getString("register_date"));
        user.setType(rs.getString("type"));
        user.setPassword(rs.getString("password"));
        return user;
    };

    public InternalUserJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<InternalUser> list() {
        String sql =
                "SELECT id, username, email, verified, register_date, type, internal_u_id, password " +
                "FROM internal_users";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(InternalUser user) {
        String sql =
                "INSERT INTO internal_users (username, email, password) VALUES " +
                "(?, ?, ?)";

        int insert = jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
        if (insert == 1)
            log.info("User " + user.getUsername() + " succesfully created");

    }

    @Override
    public Optional<InternalUser> get(Integer id) {
        return Optional.empty();
    }

    public Optional<InternalUser> get(String username) {
        String sql =
                "SELECT id, username, email, verified, register_date, type, internal_u_id, password " +
                "FROM internal_users WHERE username = ?";

        InternalUser user = null;
        try {
            user = (InternalUser) jdbcTemplate.queryForObject(sql, new Object[]{username}, rowMapper);
        } catch (DataAccessException e) {
            log.info("Username: " + username + " doesn't exist;");
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void update(InternalUser internalUserModel, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}

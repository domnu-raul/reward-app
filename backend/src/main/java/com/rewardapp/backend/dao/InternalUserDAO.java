package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.InternalUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class InternalUserDAO {
    private static final RowMapper<InternalUser> rowMapper = (rs, rowNum) -> {
        InternalUser user = new InternalUser();
        user.setId(rs.getLong("id"));
        user.setPassword(rs.getString("password"));
        return user;
    };
    private final JdbcTemplate jdbcTemplate;


    public InternalUserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InternalUser getInternalUserById(Long id) {
        String sql = "SELECT * FROM internal_users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Boolean save(InternalUser user) {
        String sql = "INSERT INTO internal_users (id, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user.getId(), user.getPassword()) == 1;
    }
}

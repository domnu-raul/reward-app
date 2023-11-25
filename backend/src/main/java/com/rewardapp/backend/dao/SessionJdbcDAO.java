package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.SessionModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SessionJdbcDAO implements DAO<SessionModel> {
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<SessionModel> rowMapper = (rs, rowNum) -> {
        SessionModel session = new SessionModel();
        session.setId(rs.getInt("id"));
        session.setSession_id(rs.getString("session_id"));
        session.setExpiration_date(rs.getString("expiration_date"));
        session.setUser_id(rs.getInt("user_id"));
        return session;
    };

    public SessionJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SessionModel> list() {
        String sql = "SELECT * FROM sessions";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(SessionModel sessionModel) {

    }

    @Override
    public Optional<SessionModel> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public void update(SessionModel sessionModel, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}

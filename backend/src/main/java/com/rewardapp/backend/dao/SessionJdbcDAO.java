package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SessionJdbcDAO implements DAO<Session> {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(SessionJdbcDAO.class);

    private static final RowMapper<Session> rowMapper = (rs, rowNum) -> {
        Session session = new Session();
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
    public List<Session> list() {
        String sql = "SELECT * FROM sessions";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Session session) {
        String sql = "INSERT INTO sessions (session_id, user_id) VALUES (?, ?)";
        int insert = jdbcTemplate.update(sql, session.getSession_id(), session.getUser_id());
        if (insert == 1 ) {
            log.info("New session created: " + session.getSession_id());
        }
    }

    @Override
    public Optional<Session> get(Integer id) {
        String sql = "SELECT * FROM sessions WHERE id = ?";
        Session session = null;
        try {
            session = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        } catch (DataAccessException e) {
            log.info("Session with id: " + id + " was not found;");
        }
        return Optional.ofNullable(session);
    }

    public Optional<Session> get(String session_id) {
        String sql =
                "SELECT id, session_id, expiration_date, user_id " +
                "FROM sessions WHERE session_id = ?";
        Session session = null;
        try {
            session = jdbcTemplate.queryForObject(sql, new Object[]{session_id}, rowMapper);
        } catch (DataAccessException e) {
            log.info("Session with session_id: " + session_id + " was not found;");
        }
        return Optional.ofNullable(session);
    }

    @Override
    public void update(Session session, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}

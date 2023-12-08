package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SessionDAO {
    private static final RowMapper<Session> rowMapper = RowMappers.sessionMapper;

    private final JdbcTemplate jdbcTemplate;

    public Session getSessionById(Long id) {
        String sql = "SELECT * FROM sessions WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Optional<Session> findSessionBySessionId(String sessionId) {
        String sql = "SELECT * FROM sessions WHERE session_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, sessionId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void removeSessionBySessionId(String sessionId) {
        String sql = "DELETE FROM sessions WHERE session_id LIKE ?";
        jdbcTemplate.update(sql, sessionId);
    }

    public Integer removeExpiredSessions() {
        String sql = "DELETE FROM sessions WHERE expiration_date < now()";
        return (Integer) jdbcTemplate.update(sql);
    }

    public Session save(Session session) {
        String sql = "INSERT INTO sessions (session_id, user_id) " +
                "VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, session.getSessionId());
            ps.setLong(2, session.getUserId());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        return new Session(
                (Long) keys.get("id"),
                session.getSessionId(),
                session.getExpirationDate(),
                session.getUserId()
        );
    }
}

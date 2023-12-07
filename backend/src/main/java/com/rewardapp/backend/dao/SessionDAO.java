package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SessionDAO {
    private static final RowMapper<Session> rowMapper = (rs, rowNum) -> {
        return Session.builder()
                .id(rs.getLong("id"))
                .sessionId(rs.getString("session_id"))
                .expirationDate(rs.getDate("expiration_date"))
                .userId(rs.getLong("user_id"))
                .build();
    };

    private final JdbcTemplate jdbcTemplate;

    public Session getSessionById(Long id) {
        String sql = "SELECT * FROM sessions WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Session getSessionBySessionId(String sessionId) {
        String sql = "SELECT * FROM sessions WHERE session_id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, sessionId);
    }

    public void removeSessionBySessionId(String sessionId) {
        String sql = "DELETE FROM sessions WHERE session_id = ?";
        jdbcTemplate.update(sql, rowMapper, sessionId);
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

        return Session.builder()
                .userId((Long) keys.get("user_id"))
                .sessionId((String) keys.get("session_id"))
                .expirationDate((Date) keys.get("expiration_date"))
                .id((Long) keys.get("id"))
                .build();
    }
}

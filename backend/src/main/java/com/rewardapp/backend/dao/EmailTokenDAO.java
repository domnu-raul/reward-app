package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.EmailToken;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailTokenDAO {
    private static final RowMapper<EmailToken> rowMapper = RowMappers.EMAIL_TOKEN_ROW_MAPPER;
    private final JdbcTemplate jdbcTemplate;

    public Optional<EmailToken> findByToken(String token) {
        String sql = "SELECT * FROM email_tokens WHERE token = ?";

        try {
            EmailToken emailToken = jdbcTemplate.queryForObject(sql, rowMapper, token);
            return Optional.of(emailToken);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<EmailToken> removeExpiredTokens() {
        String select_sql = "SELECT * FROM email_tokens WHERE expiration_date < now()";
        List<EmailToken> out = jdbcTemplate.query(select_sql, rowMapper);

        String delete_sql = "DELETE FROM email_tokens WHERE expiration_date < now()";
        jdbcTemplate.update(delete_sql);

        return out;
    }

    public EmailToken save(EmailToken emailToken) {
        String sql = "INSERT INTO email_tokens (token, user_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, emailToken.getToken());
            ps.setLong(2, emailToken.getUserId());
            return ps;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();

        return new EmailToken(
                (Long) keys.get("id"),
                (String) keys.get("token"),
                (Date) keys.get("expiration_date"),
                (Long) keys.get("user_id")
        );
    }
}

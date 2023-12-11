package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.Purchase;
import com.rewardapp.backend.models.PurchaseOption;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PurchaseDAO {
    private static final RowMapper<Purchase> PURCHASE_ROW_MAPPER = (rs, rowNum) -> new Purchase(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("purchase_option_id"),
            rs.getDate("timestamp").toString(),
            rs.getLong("cost")
    );

    private static final RowMapper<PurchaseOption> PURCHASE_OPTION_ROW_MAPPER = (rs, rowNum) -> new PurchaseOption(
            rs.getLong("id"),
            rs.getLong("value"),
            rs.getLong("cost"),
            rs.getString("currency"),
            rs.getString("name")
    );

    private final JdbcTemplate jdbcTemplate;

    public Purchase save(Long userId, Long optionId) {
        String sql = "INSERT INTO purchases (user_id, purchase_option_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userId);
            ps.setLong(2, optionId);
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        return new Purchase(
                (Long) keys.get("id"),
                userId,
                optionId,
                keys.get("timestamp").toString(),
                (Long) keys.get("cost")
        );
    }

    public PurchaseOption getOptionById(Long id) {
        String sql = "SELECT * FROM purchase_options WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, PURCHASE_OPTION_ROW_MAPPER, id);
    }

    public List<Purchase> getPurchasesByUserId(Long userId, Integer page) {
        String sql = "SELECT * FROM purchases WHERE user_id = ? ORDER BY timestamp DESC LIMIT 20 OFFSET ?";
        return jdbcTemplate.query(sql, PURCHASE_ROW_MAPPER, userId, page * 20);
    }

    public List<PurchaseOption> getOptions() {
        String sql = "SELECT * FROM purchase_options";
        return jdbcTemplate.query(sql, PURCHASE_OPTION_ROW_MAPPER);
    }
}

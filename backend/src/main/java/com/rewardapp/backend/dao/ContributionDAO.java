package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.Contribution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class ContributionDAO {
    private static final RowMapper<Contribution> rowMapper = (rs, rownum) -> new Contribution(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("recycling_center_id"),
            rs.getLong("material_id"),
            rs.getTimestamp("timestamp").toString(),
            rs.getDouble("quantity"),
            Contribution.MeasurementType.valueOf(rs.getString("measurment")),
            rs.getLong("reward")
    );
    private final JdbcTemplate jdbcTemplate;

    public ContributionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Contribution getContributionById(Long id) {
        String sql = "SELECT * FROM contributions WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Contribution> getContributionsByUserId(Long userId) {
        String sql = "SELECT * FROM contributions WHERE user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public Contribution save(Contribution contribution) {
        String sql = "INSERT INTO contributions (user_id, recycling_center_id, quantity, measurment, material_id) VALUES (?, ?, ?, ?::measurment_type, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, contribution.getUserId());
            ps.setLong(2, contribution.getRecyclingCenterId());
            ps.setDouble(3, contribution.getQuantity());
            ps.setString(4, contribution.getMeasurement().toString());
            ps.setLong(5, contribution.getMaterialId());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        return new Contribution(
                (Long) keys.get("id"),
                contribution.getUserId(),
                contribution.getRecyclingCenterId(),
                contribution.getMaterialId(),
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                contribution.getQuantity(),
                contribution.getMeasurement(),
                contribution.getReward()
        );
    }
}

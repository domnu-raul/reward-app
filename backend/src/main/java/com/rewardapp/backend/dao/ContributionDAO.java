package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.Contribution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
public class ContributionDAO {
    private static final RowMapper<Contribution> rowMapper = (rs, rownum) -> Contribution.builder()
            .id(rs.getLong("id"))
            .userId(rs.getLong("user_id"))
            .recyclingCenterId(rs.getLong("recycling_center_id"))
            .materialId(rs.getLong("material_id"))
            .timestamp(rs.getTimestamp("timestamp"))
            .quantity(rs.getDouble("quantity"))
            .measurement(Contribution.MeasurementType.valueOf(rs.getString("measurment")))
            .reward(rs.getLong("reward"))
            .build();
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

        return Contribution.builder()
                .id((Long) keys.get("id"))
                .userId((Long) keys.get("user_id"))
                .recyclingCenterId((Long) keys.get("recycling_center_id"))
                .materialId((Long) keys.get("material_id"))
                .timestamp((Timestamp) keys.get("timestamp"))
                .quantity((Double) keys.get("quantity"))
                .measurement(Contribution.MeasurementType.valueOf((String) keys.get("measurment")))
                .reward((Long) keys.get("reward"))
                .build();
    }
}

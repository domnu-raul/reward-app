package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.Contribution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContributionDAO {

    private static final RowMapper<Contribution> rowMapper = (rs, rownum) -> {
        Contribution contribution = new Contribution();
        contribution.setId(rs.getLong("id"));
        contribution.setUserId(rs.getLong("user_id"));
        contribution.setRecyclingCenterId(rs.getLong("recycling_center_id"));
        contribution.setMaterialId(rs.getLong("material_id"));
        contribution.setTimestamp(rs.getTimestamp("timestamp"));
        contribution.setQuantity(rs.getDouble("quantity"));
        contribution.setMeasurment(Contribution.MeasurmentType.valueOf(rs.getString("measurment")));
        contribution.setReward(rs.getLong("reward"));

        return contribution;
    };
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

    public Boolean save(Contribution contribution) {
        String sql = "INSERT INTO contributions (user_id, recycling_center_id, quantity, measurment, material_id) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                contribution.getUserId(),
                contribution.getRecyclingCenterId(),
                contribution.getQuantity(),
                contribution.getMeasurment(),
                contribution.getMaterialId()
        ) == 1;
    }
}

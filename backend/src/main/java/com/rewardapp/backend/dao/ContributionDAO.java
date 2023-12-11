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
    private static final RowMapper<Contribution> rowMapper = RowMappers.CONTRIBUTION_ROW_MAPPER;
    private final JdbcTemplate jdbcTemplate;

    public ContributionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Contribution getContributionById(Long id) {
        String sql = "SELECT c.id, c.measurment, c.user_id, c.recycling_center_id, c.timestamp, c.quantity, c.reward, m.name " +
                "FROM contributions c JOIN public.materials m on m.id = c.material_id WHERE c.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Contribution> getContributionsByUserId(Long userId, Integer page) {
        String sql = "SELECT c.id, c.measurment, c.user_id, c.recycling_center_id, c.timestamp, c.quantity, c.reward, m.name " +
                "FROM contributions c JOIN public.materials m on m.id = c.material_id WHERE c.user_id = ? ORDER BY c.timestamp DESC LIMIT 20 OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, userId, page * 20);
    }

    public void validate(Contribution contribution) {
        String reward_sql = "SELECT reward FROM reward_system WHERE material_id = (SELECT id FROM materials WHERE name = ?) AND measurment = ?::measurment_type";
        String validate_sql = "SELECT id FROM recycling_centers_materials " +
                "WHERE material_id = (SELECT id FROM materials WHERE name = ?)" +
                "AND recycling_center_id = ?";
        try {
            jdbcTemplate.queryForObject(validate_sql, Long.class, contribution.getMaterial(), contribution.getRecyclingCenterId());
        } catch (Exception e) {
            throw new RuntimeException("This material is not accepted by the recycling center");
        }

        Long reward;
        try {
            reward = jdbcTemplate.queryForObject(reward_sql, Long.class, contribution.getMaterial(), contribution.getMeasurement().toString());
        } catch (Exception e) {
            throw new RuntimeException("No reward for this material and measurement type");
        }

        contribution.setReward(reward * contribution.getQuantity().longValue());
    }

    public Contribution save(Contribution contribution) {
        String sql = "INSERT INTO contributions (user_id, recycling_center_id, quantity, measurment, material_id, reward) VALUES (?, ?, ?, ?::measurment_type, (SELECT id FROM materials WHERE name = ?), ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, contribution.getUserId());
            ps.setLong(2, contribution.getRecyclingCenterId());
            ps.setDouble(3, contribution.getQuantity());
            ps.setString(4, contribution.getMeasurement().toString());
            ps.setString(5, contribution.getMaterial());
            ps.setLong(6, contribution.getReward());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();

        return new Contribution(
                (Long) keys.get("id"),
                contribution.getUserId(),
                contribution.getRecyclingCenterId(),
                contribution.getMaterial(),
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                contribution.getQuantity(),
                contribution.getMeasurement(),
                (Long) keys.get("reward")
        );
    }
}

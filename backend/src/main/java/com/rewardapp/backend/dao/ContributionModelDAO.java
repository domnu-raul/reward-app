package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.ContributionModel;
import com.rewardapp.backend.models.LocationModel;
import com.rewardapp.backend.models.RecyclingCenterModel;
import com.rewardapp.backend.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContributionModelDAO {
    private static final RowMapper<ContributionModel> rowMapper = (rs, rowNum) -> new ContributionModel(
            rs.getLong("c_id"),
            new UserModel(
                    rs.getLong("u_id"),
                    rs.getString("username"),
                    rs.getString("u_email"),
                    rs.getBoolean("u_verified"),
                    rs.getDate("u_register_date"),
                    UserModel.UserType.valueOf(rs.getString("type"))
            ),
            new RecyclingCenterModel(
                    rs.getLong("rc_id"),
                    rs.getString("rc_name"),
                    null,
                    new LocationModel(
                            rs.getString("l_county"),
                            rs.getString("l_city"),
                            rs.getString("l_address"),
                            rs.getString("l_zipcode"),
                            rs.getDouble("l_longitude"),
                            rs.getDouble("l_latitude")
                    ),
                    rs.getTime("rc_start_time"),
                    rs.getTime("rc_end_time")
            ),
            rs.getString("m_name"),
            rs.getTimestamp("c_timestamp").toString(),
            rs.getDouble("c_quantity"),
            rs.getString("c_measurment"),
            rs.getLong("c_reward")
    );

    private final JdbcTemplate jdbcTemplate;

    public Optional<ContributionModel> getContributionDetails(Long id) {
        String sql = "SELECT " +
                "c.id AS c_id, " +
                "c.user_id AS u_id, " +
                "c.recycling_center_id AS rc_id, " +
                "c.timestamp AS c_timestamp, " +
                "c.quantity AS c_quantity, " +
                "c.measurment::varchar AS c_measurment, " +
                "c.reward AS c_reward, " +
                "u.username, " +
                "u.email AS u_email, " +
                "u.verified AS u_verified, " +
                "u.register_date AS u_register_date, " +
                "rc.name AS rc_name, " +
                "rc.start_time AS rc_start_time, " +
                "rc.end_time AS rc_end_time, " +
                "m.name AS m_name, " +
                "l.county AS l_county, " +
                "l.city AS l_city, " +
                "l.address AS l_address, " +
                "l.zipcode AS l_zipcode, " +
                "l.longitude AS l_longitude, " +
                "l.latitude AS l_latitude " +
                "FROM contributions c " +
                "JOIN users u ON c.user_id = u.id " +
                "LEFT JOIN recycling_centers rc ON c.recycling_center_id = rc.id " +
                "LEFT JOIN recycling_centers_locations l ON rc.id = l.recycling_center_id " +
                "JOIN public.materials m ON m.id = c.material_id " +
                "WHERE c.id = ?";

        try {
            ContributionModel contributionModel = jdbcTemplate.queryForObject(sql, rowMapper, id);
            String materials_sql = "SELECT m.name FROM materials m JOIN contributions c ON m.id = c.material_id WHERE c.id = ?";
            contributionModel.getRecyclingCenter().setMaterials(jdbcTemplate.queryForList(materials_sql, String.class, id));

            return Optional.of(contributionModel);
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}

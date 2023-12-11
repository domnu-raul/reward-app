package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.ContributionDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContributionDetailsDAO {
    private final JdbcTemplate jdbcTemplate;

    public Optional<ContributionDetails> getContributionDetails(Long id) {
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
                "u.type AS u_type, " +
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

        ContributionDetails contributionDetails = jdbcTemplate.queryForObject(sql, RowMappers.CONTRIBUTION_DETAILS_ROW_MAPPER, id);

        if (contributionDetails.getRecyclingCenter() != null) {
            String materials_sql = "SELECT m.name FROM materials m JOIN contributions c ON m.id = c.material_id WHERE c.id = ?";
            contributionDetails.getRecyclingCenter().setMaterials(jdbcTemplate.queryForList(materials_sql, String.class, id));
        }

        return Optional.ofNullable(contributionDetails);
    }
}

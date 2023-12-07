package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.Material;
import com.rewardapp.backend.entities.RcLocation;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.models.RecyclingCenterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RecyclingCenterDAO {
    private static final RowMapper<RecyclingCenter> rowMapper = (rs, rowNum) -> {

        RcLocation location = RcLocation.builder()
                .county(rs.getString("county"))
                .city(rs.getString("city"))
                .address(rs.getString("address"))
                .zipcode(rs.getString("zipcode"))
                .id(rs.getLong("recycling_center_id"))
                .latitude(rs.getDouble("latitude"))
                .longitude(rs.getDouble("longitude"))
                .build();

        if (location.getLatitude() == 0D) location.setLatitude(null);
        if (location.getLongitude() == 0D) location.setLongitude(null);

        return RecyclingCenter.builder()
                .location(location)
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .startingTime(rs.getTime("start_time"))
                .endTime(rs.getTime("end_time"))
                .build();
    };

    private final JdbcTemplate jdbcTemplate;

    public RecyclingCenter getRecyclingCenterById(Long id) {
        String rc_sql =
                "SELECT * FROM recycling_centers AS rc " +
                        "JOIN public.recycling_centers_locations AS rcl " +
                        "on rc.id = rcl.recycling_center_id WHERE rc.id = ?";

        String materials_sql =
                "SELECT m.id, m.name FROM recycling_centers_materials rcm " +
                        "JOIN public.materials m on m.id = rcm.material_id " +
                        "WHERE  rcm.recycling_center_id = ?";

        List<Material> materials = jdbcTemplate.queryForList(materials_sql, id)
                .stream()
                .map(row -> Material.builder()
                        .id((Long) row.get("id"))
                        .name((String) row.get("name"))
                        .build())
                .toList();

        RecyclingCenter recyclingCenter = jdbcTemplate.queryForObject(rc_sql, rowMapper, id);
        recyclingCenter.setMaterials(materials);
        return recyclingCenter;
    }

    public List<RecyclingCenter> getAll() {
        String rc_sql =
                "SELECT * FROM recycling_centers AS rc " +
                        "JOIN public.recycling_centers_locations AS rcl " +
                        "on rc.id = rcl.recycling_center_id";

        String materials_sql =
                "SELECT m.id, m.name FROM recycling_centers_materials rcm " +
                        "JOIN public.materials m on m.id = rcm.material_id " +
                        "WHERE  rcm.recycling_center_id = ?";

        return jdbcTemplate.query(rc_sql, rowMapper)
                .stream()
                .peek(rc -> {
                    List<Material> materials = jdbcTemplate.queryForList(materials_sql, rc.getId())
                            .stream()
                            .map(row -> Material.builder()
                                    .id((Long) row.get("id"))
                                    .name((String) row.get("name"))
                                    .build())
                            .toList();
                    rc.setMaterials(materials);
                })
                .toList();
    }

    public RecyclingCenter save(RecyclingCenterModel recyclingCenterModel) {
        String rc_sql =
                "INSERT INTO recycling_centers (name, start_time, end_time) " +
                        "VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(rc_sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, recyclingCenterModel.getName());
            ps.setTime(2, recyclingCenterModel.getStartTime());
            ps.setTime(3, recyclingCenterModel.getEndTime());
            return ps;
        }, keyHolder);

        Map<String, Object> rc_keys = keyHolder.getKeys();

        RecyclingCenter recyclingCenter = RecyclingCenter.builder()
                .id((Long) rc_keys.get("id"))
                .name((String) rc_keys.get("name"))
                .startingTime((Time) rc_keys.get("start_time"))
                .endTime((Time) rc_keys.get("end_time"))
                .build();

        String location_sql =
                "INSERT INTO recycling_centers_locations (recycling_center_id, county, city, address, zipcode, latitude, longitude) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(location_sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, recyclingCenter.getId());
            ps.setString(2, recyclingCenterModel.getLocation().getCounty());
            ps.setString(3, recyclingCenterModel.getLocation().getCity());
            ps.setString(4, recyclingCenterModel.getLocation().getAddress());
            ps.setString(5, recyclingCenterModel.getLocation().getZipcode());
            ps.setDouble(6, recyclingCenterModel.getLocation().getLatitude());
            ps.setDouble(7, recyclingCenterModel.getLocation().getLongitude());
            return ps;
        }, keyHolder);

        Map<String, Object> location_keys = keyHolder.getKeys();

        RcLocation location = RcLocation.builder()
                .county(location_keys.get("county").toString())
                .city(location_keys.get("city").toString())
                .address(location_keys.get("address").toString())
                .zipcode(location_keys.get("zipcode").toString())
                .longitude((Double) location_keys.get("longitude"))
                .latitude((Double) location_keys.get("latitude"))
                .id((Long) location_keys.get("id"))
                .build();

        recyclingCenter.setLocation(location);

        String find_materials_sql = "SELECT id FROM materials WHERE name = ?";
        String insert_materials_sql = "INSERT INTO recycling_centers_materials (recycling_center_id, material_id) VALUES (?, ?)";

        List<Material> materials = recyclingCenterModel.getMaterials()
                .stream()
                .map(material -> {
                    Long material_id = jdbcTemplate.queryForObject(find_materials_sql, Long.class, material);
                    return Material.builder()
                            .id(material_id)
                            .name(material)
                            .build();
                })
                .toList();

        materials.forEach(material -> {
            jdbcTemplate.update(insert_materials_sql, recyclingCenter.getId(), material.getId());
        });

        recyclingCenter.setMaterials(materials);

        return recyclingCenter;
    }

    public RecyclingCenter update(Long id, RecyclingCenterModel recyclingCenterModel) {
        String rc_sql = "UPDATE recycling_centers SET name = ?, start_time = ?, end_time = ? WHERE id = ?";

        jdbcTemplate.update(rc_sql,
                recyclingCenterModel.getName(),
                recyclingCenterModel.getStartTime(),
                recyclingCenterModel.getEndTime(), id);

        String location_sql =
                "UPDATE recycling_centers_locations SET county = ?, city = ?, address = ?, zipcode = ?, latitude = ?, longitude = ? WHERE recycling_center_id = ?";

        jdbcTemplate.update(location_sql,
                recyclingCenterModel.getLocation().getCounty(),
                recyclingCenterModel.getLocation().getCity(),
                recyclingCenterModel.getLocation().getAddress(),
                recyclingCenterModel.getLocation().getZipcode(),
                recyclingCenterModel.getLocation().getLatitude(),
                recyclingCenterModel.getLocation().getLongitude(), id);

        String delete_materials_sql = "DELETE FROM recycling_centers_materials WHERE recycling_center_id = ?";
        String find_materials_sql = "SELECT id FROM materials WHERE name = ?";
        String insert_materials_sql = "INSERT INTO recycling_centers_materials (recycling_center_id, material_id) VALUES (?, ?)";

        jdbcTemplate.update(delete_materials_sql, id);

        List<Material> materials = recyclingCenterModel.getMaterials()
                .stream()
                .map(material -> {
                    Long material_id = jdbcTemplate.queryForObject(find_materials_sql, Long.class, material);
                    return Material.builder()
                            .id(material_id)
                            .name(material)
                            .build();
                })
                .toList();

        materials.forEach(material -> {
            jdbcTemplate.update(insert_materials_sql, id, material.getId());
        });

        return getRecyclingCenterById(id);
    }

    public Boolean deleteById(Long id) {
        String sql = "DELETE FROM recycling_centers rc WHERE id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }
}

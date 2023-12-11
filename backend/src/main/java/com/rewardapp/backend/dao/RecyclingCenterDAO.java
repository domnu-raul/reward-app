package com.rewardapp.backend.dao;

import com.querydsl.sql.SQLQueryFactory;
import com.rewardapp.backend.entities.Material;
import com.rewardapp.backend.models.Location;
import com.rewardapp.backend.models.RecyclingCenter;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecyclingCenterDAO {
    private static final RowMapper<RecyclingCenter> rowMapper = RowMappers.RECYCLING_CENTER_ROW_MAPPER;

    private final JdbcTemplate jdbcTemplate;

    public RecyclingCenter getRecyclingCenterById(Long id) {
        String sql = "SELECT * FROM recycling_centers_view WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<RecyclingCenter> getAll(List<String> materials, String search,
            String order, Boolean reverse,
            Boolean open, Integer page) {

        List<String> where = new ArrayList<>();
        List<String> order_by = new ArrayList<>();

        if (open != null) {
            String open_filter = "(CURRENT_TIME BETWEEN start_time AND end_time)";
            if (!open)
                open_filter = "NOT " + open_filter;

            where.add(open_filter);
        }

        if (materials != null && !materials.isEmpty()) {
            String materials_filter = "ARRAY[" +
                    String.join(", ",
                            materials.stream().map(x -> "'" + x + "'").toList())
                    +
                    "]::varchar[] <@ materials";
            where.add(materials_filter);
        }

        if (search != null && !search.isEmpty()) {
            order_by.add(String.format(
                    new StringBuilder()
                            .append("GREATEST(")
                            .append("SIMILARITY(METAPHONE(name, 10), METAPHONE('%s', 10)), ")
                            .append("SIMILARITY(METAPHONE(")
                            .append("CONCAT_WS(' ', county, city, address, zipcode), 10), ")
                            .append("METAPHONE('%s', 10))) DESC")
                            .toString(),
                    search, search));
        }

        if (order != null && !order.isEmpty()) {
            order_by.add(order + (reverse ? " DESC" : " ASC"));
        }

        String where_filter = where.isEmpty() ? "" : "WHERE " + String.join(" AND ", where);
        String order_by_filter = order_by.isEmpty() ? "" : "ORDER BY " + String.join(", ", order_by);

        String sql = "SELECT * FROM recycling_centers_view " + where_filter + " " +
                order_by_filter + " "
                + "LIMIT 20 OFFSET " + page * 20;

        return jdbcTemplate.query(sql, rowMapper);
    }

    public RecyclingCenter save(RecyclingCenter entity) {
        String rc_sql = "INSERT INTO recycling_centers (name, start_time, end_time) "
                + "VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(rc_sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            ps.setTime(2, entity.getStartTime());
            ps.setTime(3, entity.getEndTime());
            return ps;
        }, keyHolder);

        Map<String, Object> rc_keys = keyHolder.getKeys();

        RecyclingCenter recyclingCenter = new RecyclingCenter(
                (Long) rc_keys.get("id"), (String) rc_keys.get("name"), null, null,
                (Time) rc_keys.get("start_time"), (Time) rc_keys.get("end_time"));

        String location_sql = "INSERT INTO recycling_centers_locations (recycling_center_id, county, city, address, zipcode, latitude, longitude) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(location_sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, recyclingCenter.getId());
            ps.setString(2, entity.getLocation().getCounty());
            ps.setString(3, entity.getLocation().getCity());
            ps.setString(4, entity.getLocation().getAddress());
            ps.setString(5, entity.getLocation().getZipcode());
            ps.setDouble(6, entity.getLocation().getLatitude());
            ps.setDouble(7, entity.getLocation().getLongitude());
            return ps;
        }, keyHolder);

        Map<String, Object> location_keys = keyHolder.getKeys();

        Location location = new Location(location_keys.get("county").toString(),
                location_keys.get("city").toString(),
                location_keys.get("address").toString(),
                location_keys.get("zipcode").toString(),
                (Double) location_keys.get("longitude"),
                (Double) location_keys.get("latitude"));

        recyclingCenter.setLocation(location);

        String find_materials_sql = "SELECT id FROM materials WHERE name = ?";
        String insert_materials_sql = "INSERT INTO recycling_centers_materials (recycling_center_id, material_id) VALUES (?, ?)";

        List<Material> materials = entity.getMaterials()
                .stream()
                .map(material -> {
                    Long material_id = jdbcTemplate.queryForObject(
                            find_materials_sql, Long.class, material);
                    return Material.builder().id(material_id).name(material).build();
                })
                .toList();

        materials.forEach(material -> {
            jdbcTemplate.update(insert_materials_sql, recyclingCenter.getId(),
                    material.getId());
        });

        recyclingCenter.setMaterials(entity.getMaterials());

        return recyclingCenter;
    }

    public RecyclingCenter update(Long id, RecyclingCenter recyclingCenter) {
        String rc_sql = "UPDATE recycling_centers SET name = ?, start_time = ?, end_time = ? WHERE id = ?";

        jdbcTemplate.update(rc_sql, recyclingCenter.getName(),
                recyclingCenter.getStartTime(),
                recyclingCenter.getEndTime(), id);

        String location_sql = "UPDATE recycling_centers_locations SET county = ?, city = ?, address = ?, zipcode = ?, latitude = ?, longitude = ? WHERE recycling_center_id = ?";

        jdbcTemplate.update(location_sql, recyclingCenter.getLocation().getCounty(),
                recyclingCenter.getLocation().getCity(),
                recyclingCenter.getLocation().getAddress(),
                recyclingCenter.getLocation().getZipcode(),
                recyclingCenter.getLocation().getLatitude(),
                recyclingCenter.getLocation().getLongitude(), id);

        String delete_materials_sql = "DELETE FROM recycling_centers_materials WHERE recycling_center_id = ?";
        String find_materials_sql = "SELECT id FROM materials WHERE name = ?";
        String insert_materials_sql = "INSERT INTO recycling_centers_materials (recycling_center_id, material_id) VALUES (?, ?)";

        jdbcTemplate.update(delete_materials_sql, id);

        List<Material> materials = recyclingCenter.getMaterials()
                .stream()
                .map(material -> {
                    Long material_id = jdbcTemplate.queryForObject(
                            find_materials_sql, Long.class, material);
                    return Material.builder().id(material_id).name(material).build();
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

    public List<RecyclingCenter> getClosest(Double lat, Double lng) {
        String sql = "select * from (SELECT rc.*, rcl.*, ARRAY_AGG(m.name)as materials FROM recycling_centers AS rc "
                +
                "LEFT JOIN public.recycling_centers_locations AS rcl ON rc.id = rcl.recycling_center_id "
                +
                "LEFT JOIN public.recycling_centers_materials rcm on rc.id = rcm.recycling_center_id "
                + "LEFT JOIN public.materials m on rcm.material_id = m.id "
                + "GROUP BY rc.id, rcl.recycling_center_id) "
                + "ORDER BY (abs(latitude - ?), abs(longitude - ?)) ASC LIMIT 20";

        return jdbcTemplate.query(sql, rowMapper, lat, lng);
    }
}

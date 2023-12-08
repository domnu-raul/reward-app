package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.entities.InternalUser;
import com.rewardapp.backend.models.*;
import org.springframework.jdbc.core.RowMapper;

public class RowMappers {
    public static final RowMapper<User> userMapper = (rs, rownum) -> new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getBoolean("verified"),
            rs.getDate("register_date").toString(),
            User.UserType.valueOf(rs.getString("type"))
    );
    public static final RowMapper<InternalUser> internalUserMapper = (rs, rowNum) -> new InternalUser(
            rs.getLong("id"),
            rs.getString("password")
    );

    public static final RowMapper<Contribution> contributionMapper = (rs, rownum) -> new Contribution(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("recycling_center_id"),
            rs.getString("name"),
            rs.getTimestamp("timestamp").toString(),
            rs.getDouble("quantity"),
            Contribution.MeasurementType.valueOf(rs.getString("measurment")),
            rs.getLong("reward")
    );
    public static final RowMapper<RecyclingCenter> recyclingCenterMapper = (rs, rowNum) -> {

        Location location = new Location(
                rs.getString("county"),
                rs.getString("city"),
                rs.getString("address"),
                rs.getString("zipcode"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude")
        );

        if (location.getLatitude() == 0D) location.setLatitude(null);
        if (location.getLongitude() == 0D) location.setLongitude(null);

        return new RecyclingCenter(
                rs.getLong("id"),
                rs.getString("name"),
                null,
                location,
                rs.getTime("start_time"),
                rs.getTime("end_time")
        );
    };

    public static final RowMapper<EmailToken> emailTokenMapper = (rs, rowNum) -> new EmailToken(
            rs.getLong("id"),
            rs.getString("token"),
            rs.getDate("expiration_date"),
            rs.getLong("user_id")
    );
    public static final RowMapper<Session> sessionMapper = (rs, rowNum) -> new Session(
            rs.getLong("id"),
            rs.getString("session_id"),
            rs.getDate("expiration_date"),
            rs.getLong("user_id")
    );
}

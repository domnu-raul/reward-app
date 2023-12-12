package com.rewardapp.backend.dao;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.entities.InternalUser;
import com.rewardapp.backend.models.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class RowMappers {
    public static final RowMapper<User> USER_ROW_MAPPER = (rs, rownum) -> new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getBoolean("verified"),
            rs.getDate("register_date").toString(),
            User.UserType.valueOf(rs.getString("type"))
    );
    public static final RowMapper<InternalUser> INTERNAL_USER_ROW_MAPPER = (rs, rowNum) -> new InternalUser(
            rs.getLong("id"),
            rs.getString("password")
    );

    public static final RowMapper<Contribution> CONTRIBUTION_ROW_MAPPER = (rs, rownum) -> new Contribution(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("recycling_center_id") != 0 ? rs.getLong("recycling_center_id") : null,
            rs.getString("name"),
            rs.getTimestamp("timestamp").toString(),
            rs.getDouble("quantity"),
            Contribution.MeasurementType.valueOf(rs.getString("measurment")),
            rs.getLong("reward")
    );
    public static final RowMapper<RecyclingCenter> RECYCLING_CENTER_ROW_MAPPER = (rs, rowNum) -> {

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

        Array materials = rs.getArray("materials");
        List<String> materialsList = null;
        if (materials != null)
            materialsList = Arrays.asList((String[]) materials.getArray());

        return new RecyclingCenter(
                rs.getLong("id"),
                rs.getString("name"),
                //needs to convert the array in a list
                materialsList,
                location,
                rs.getTime("start_time"),
                rs.getTime("end_time")
        );
    };

    public static final RowMapper<EmailToken> EMAIL_TOKEN_ROW_MAPPER = (rs, rowNum) -> new EmailToken(
            rs.getLong("id"),
            rs.getString("token"),
            rs.getDate("expiration_date"),
            rs.getLong("user_id")
    );
    public static final RowMapper<Session> SESSION_ROW_MAPPER = (rs, rowNum) -> new Session(
            rs.getLong("id"),
            rs.getString("session_id"),
            rs.getDate("expiration_date"),
            rs.getLong("user_id")
    );

    public static final RowMapper<UserDetails> USER_DETAILS_ROW_MAPPER = (rs, rowNum) -> new UserDetails(
            rs.getInt("current_points"),
            rs.getInt("total_points"),
            rs.getInt("total_contributions"),
            rs.getInt("total_vouchers"),
            rs.getInt("total_used_vouchers"),
            rs.getInt("money_earned")
    );
    public static final RowMapper<ContributionDetails> CONTRIBUTION_DETAILS_ROW_MAPPER = (rs, rowNum) -> new ContributionDetails(
            rs.getLong("c_id"),
            new User(
                    rs.getLong("u_id"),
                    rs.getString("username"),
                    rs.getString("u_email"),
                    rs.getBoolean("u_verified"),
                    rs.getDate("u_register_date").toString(),
                    User.UserType.valueOf(rs.getString("u_type"))
            ),
            rs.getLong("rc_id") != 0 ? new RecyclingCenter(
                    rs.getLong("rc_id"),
                    rs.getString("rc_name"),
                    null,
                    new Location(
                            rs.getString("l_county"),
                            rs.getString("l_city"),
                            rs.getString("l_address"),
                            rs.getString("l_zipcode"),
                            rs.getDouble("l_longitude"),
                            rs.getDouble("l_latitude")
                    ),
                    rs.getTime("rc_start_time"),
                    rs.getTime("rc_end_time")
            ) : null,
            rs.getString("m_name"),
            rs.getTimestamp("c_timestamp").toString(),
            rs.getDouble("c_quantity"),
            rs.getString("c_measurment"),
            rs.getLong("c_reward")
    );

    public static final RowMapper<Voucher> VOUCHER_ROW_MAPPER = (rs, rowNum) -> new Voucher(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("purchase_id"),
            rs.getDate("expiration_date").toString(),
            rs.getInt("value"),
            rs.getString("currency"),
            rs.getBoolean("used"),
            rs.getBoolean("active")
    );

}

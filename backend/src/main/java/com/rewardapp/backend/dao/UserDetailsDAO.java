package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDataDAO {

    private static final RowMapper<User> userMapper = RowMappers.userMapper;
    private static final RowMapper<UserStatistics> statisticsMapper = (rs, rowNum) -> new UserStatistics(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("current_points"),
            rs.getInt("total_points"),
            rs.getInt("total_contributions"),
            rs.getInt("total_vouchers"),
            rs.getInt("total_used_vouchers")
    );
    private static final RowMapper<Voucher> voucherMapper = (rs, rowNum) -> new Voucher(
            rs.getLong("id"),
            rs.getLong("user_id"),
            rs.getLong("purchase_id"),
            rs.getDate("expiration_date").toString(),
            rs.getInt("value"),
            rs.getString("currency"),
            rs.getBoolean("active"),
            rs.getBoolean("used")
    );
    private static final RowMapper<Contribution> contributionMapper = RowMappers.contributionMapper;
    private final JdbcTemplate jdbcTemplate;

    public UserData getUserData(Long userId) {
        String contribution_sql = "SELECT c.id, c.measurment, c.user_id, c.recycling_center_id, c.timestamp, c.quantity, c.reward, m.name " +
                "FROM contributions c JOIN public.materials m on m.id = c.material_id WHERE c.user_id = ? ORDER BY c.timestamp DESC";

        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", userMapper, userId);
        UserStatistics statistics = jdbcTemplate.queryForObject("SELECT * FROM user_stats WHERE user_id = ?", statisticsMapper, userId);
        List<Voucher> usedVouchers = jdbcTemplate.query("SELECT * FROM vouchers WHERE user_id = ? AND used = true", voucherMapper, userId);
        List<Voucher> activeVouchers = jdbcTemplate.query("SELECT * FROM vouchers WHERE user_id = ? AND active = true", voucherMapper, userId);
        List<Contribution> contributions = jdbcTemplate.query(contribution_sql, contributionMapper, userId);

        return new UserData(user, statistics, contributions, activeVouchers, usedVouchers);
    }
}

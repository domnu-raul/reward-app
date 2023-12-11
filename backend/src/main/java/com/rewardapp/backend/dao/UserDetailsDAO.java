package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsDAO {
    private final JdbcTemplate jdbcTemplate;

    public UserDetails getUserDetails(Long userId) {
        return jdbcTemplate.queryForObject("SELECT * FROM user_stats WHERE user_id = ?", RowMappers.USER_DETAILS_ROW_MAPPER, userId);
    }
}

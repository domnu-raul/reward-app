package com.rewardapp.backend.dao;

import com.rewardapp.backend.models.Voucher;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VoucherDAO {
    private static final RowMapper<Voucher> rowMapper = RowMappers.VOUCHER_ROW_MAPPER;
    private final JdbcTemplate jdbcTemplate;

    public List<Voucher> getVouchersByUserId(Long userId, Integer page) {
        String sql = "SELECT *, CURRENT_DATE < v.expiration_date AS active " +
                "FROM vouchers v WHERE v.user_id = ? ORDER BY v.expiration_date DESC LIMIT 20 OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, userId, page * 20);
    }

    public List<Voucher> getActiveVouchersByUserId(Long userId, Integer page) {
        String sql = "SELECT *, CURRENT_DATE < v.expiration_date AS active " +
                "FROM vouchers v WHERE v.user_id = ? AND CURRENT_DATE < v.expiration_date ORDER BY v.expiration_date DESC LIMIT 20 OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, userId, page * 20);
    }

    public List<Voucher> getExpiredVouchersByUserId(Long userId, Integer page) {
        String sql = "SELECT *, CURRENT_DATE < v.expiration_date AS active " +
                "FROM vouchers v WHERE v.user_id = ? AND CURRENT_DATE >= v.expiration_date ORDER BY v.expiration_date DESC LIMIT 20 OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, userId, page * 20);
    }

    public Optional<Voucher> findVoucherByPurchaseId(Long purchaseId) {
        String sql = "SELECT *, CURRENT_DATE < vouchers.expiration_date AS active " +
                "FROM vouchers WHERE purchase_id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, purchaseId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

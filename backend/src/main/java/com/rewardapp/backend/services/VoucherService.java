package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.VoucherDAO;
import com.rewardapp.backend.models.Voucher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherDAO voucherDAO;

    public List<Voucher> getVouchersByUserId(Long userId, Integer page) {
        return voucherDAO.getVouchersByUserId(userId, page);
    }

    public List<Voucher> getActiveVouchersByUserId(Long userId, Integer page) {
        return voucherDAO.getActiveVouchersByUserId(userId, page);
    }

    public List<Voucher> getExpiredVouchersByUserId(Long userId, Integer page) {
        return voucherDAO.getExpiredVouchersByUserId(userId, page);
    }

    public Voucher getVoucherByPurchaseId(Long userId, Long purchaseId) {
        Voucher voucher = voucherDAO.findVoucherByPurchaseId(purchaseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voucher not found"));

        if (!Objects.equals(voucher.getUserId(), userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this voucher");

        return voucher;
    }
}

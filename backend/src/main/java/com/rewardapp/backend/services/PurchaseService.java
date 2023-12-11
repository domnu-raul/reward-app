package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.PurchaseDAO;
import com.rewardapp.backend.dao.UserDetailsDAO;
import com.rewardapp.backend.models.Purchase;
import com.rewardapp.backend.models.PurchaseOption;
import com.rewardapp.backend.models.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseDAO purchaseDAO;
    private final UserDetailsDAO userDetailsDAO;

    public Purchase getPurchaseById(Long id) {
        return null;
    }

    public List<Purchase> getPurchasesByUserId(Long userId, Integer page) {
        return purchaseDAO.getPurchasesByUserId(userId, page);
    }

    public List<PurchaseOption> getOptions() {
        return purchaseDAO.getOptions();
    }

    public Purchase createPurchase(Long userId, Long optionId) {
        UserDetails userDetails = userDetailsDAO.getUserDetails(userId);
        if (userDetails.getRewardPoints() < purchaseDAO.getOptionById(optionId).cost())
            throw new RuntimeException("Insufficient funds.");

        return purchaseDAO.save(userId, optionId);
    }
}

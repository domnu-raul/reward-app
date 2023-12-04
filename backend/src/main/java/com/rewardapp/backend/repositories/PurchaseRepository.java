package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    public Purchase getPurchaseByIdAndUserId(Long id, Long userId);
    public List<Purchase> getAllByUserId(Long userId);
}

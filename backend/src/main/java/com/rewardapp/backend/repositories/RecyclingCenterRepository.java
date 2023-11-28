package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.RecyclingCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, Long> {
    public RecyclingCenter getRecyclingCenterById(Long id);
}

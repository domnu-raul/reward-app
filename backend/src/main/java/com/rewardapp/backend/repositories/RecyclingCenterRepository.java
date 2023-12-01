package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.RecyclingCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, Long> {
    public RecyclingCenter getRecyclingCenterById(Long id);
    @Query(value = "SELECT * FROM recycling_centers", nativeQuery = true)
    public List<RecyclingCenter> getAll();
}

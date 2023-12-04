package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.RecyclingCenter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecyclingCenterRepository extends JpaRepository<RecyclingCenter, Long> {
    public RecyclingCenter getRecyclingCenterById(Long id);

    @Query(value = "SELECT * FROM recycling_centers", nativeQuery = true)
    public List<RecyclingCenter> getAll();

    @Transactional
    @Modifying
    @Query("UPDATE RecyclingCenterLocation l SET " +
            "l.county = :#{#object.recyclingCenterLocation.county}, " +
            "l.city = :#{#object.recyclingCenterLocation.city}, " +
            "l.address = :#{#object.recyclingCenterLocation.address}, " +
            "l.zipcode = :#{#object.recyclingCenterLocation.zipcode}, " +
            "l.latitude = :#{#object.recyclingCenterLocation.latitude}, " +
            "l.longitude = :#{#object.recyclingCenterLocation.longitude} " +
            "WHERE l.id = :#{#object.id}")
    public void updateLocation(@Param("object") RecyclingCenter object);
}

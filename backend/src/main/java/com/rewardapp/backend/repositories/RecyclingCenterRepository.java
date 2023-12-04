package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.RecyclingCenter;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecyclingCenterRepository extends CrudRepository<RecyclingCenter, Long> {
    public RecyclingCenter getRecyclingCenterById(Long id);

    @Query("SELECT * FROM recycling_centers")
    public List<RecyclingCenter> getAll();

    @Transactional
    @Modifying
    @Query("UPDATE recycling_centers_locations l SET " +
            "l.county = :#{#object.recyclingCenterLocation.county}, " +
            "l.city = :#{#object.recyclingCenterLocation.city}, " +
            "l.address = :#{#object.recyclingCenterLocation.address}, " +
            "l.zipcode = :#{#object.recyclingCenterLocation.zipcode}, " +
            "l.latitude = :#{#object.recyclingCenterLocation.latitude}, " +
            "l.longitude = :#{#object.recyclingCenterLocation.longitude} " +
            "WHERE l.id = :#{#object.id}")
    public void updateLocation(@Param("object") RecyclingCenter object);
}

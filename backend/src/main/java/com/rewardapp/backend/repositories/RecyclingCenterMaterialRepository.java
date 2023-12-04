package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.RecyclingCenterMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingCenterMaterialRepository extends JpaRepository<RecyclingCenterMaterial, Long> {

}

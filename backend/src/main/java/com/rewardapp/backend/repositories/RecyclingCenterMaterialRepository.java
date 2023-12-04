package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.RecyclingCenterMaterial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingCenterMaterialRepository extends CrudRepository<RecyclingCenterMaterial, Long> {

}

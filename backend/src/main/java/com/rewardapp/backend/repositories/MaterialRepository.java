package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.Material;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Long> {
    public Optional<Material> findMaterialByName(String name);

    public Material getMaterialById(Long id);
}

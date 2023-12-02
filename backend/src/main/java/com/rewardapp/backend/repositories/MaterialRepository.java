package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    public Optional<Material> findMaterialByName(String name);
}

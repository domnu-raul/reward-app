package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.Contribution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends CrudRepository<Contribution, Long> {

    public Contribution getContributionByIdAndUserId(Long id, Long userId);

    public List<Contribution> getAllByUserId(Long userId);
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecyclingCenterService {
    private final RecyclingCenterRepository repository;
    public RecyclingCenterService(RecyclingCenterRepository repository) {
        this.repository = repository;
    }

    public RecyclingCenter create(RecyclingCenter recyclingCenter) {
        if (recyclingCenter.getLocation() == null)
            throw new RuntimeException("Location field is mandatory.");

        return repository.save(recyclingCenter);
    }

    public RecyclingCenter getRecyclingCenterById(Long id) {
        return repository.getRecyclingCenterById(id);
    }
    public List<RecyclingCenter> getAll() {
        return repository.getAll();
    }
}

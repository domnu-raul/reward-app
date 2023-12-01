package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import org.springframework.stereotype.Service;

@Service
public class RecyclingCenterService {
    private final RecyclingCenterRepository repository;
    public RecyclingCenterService(RecyclingCenterRepository repository) {
        this.repository = repository;
    }

    public RecyclingCenter create() {
        return null;
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.ContributionDAO;
import com.rewardapp.backend.dao.ContributionDetailsDAO;
import com.rewardapp.backend.entities.Contribution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContributionService {
    private final ContributionDAO contributionDAO;
    private final ContributionDetailsDAO contributionDetailsDAO;

    public Contribution get(Long id) {
        return null;
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.ContributionDAO;
import com.rewardapp.backend.dao.ContributionDetailsDAO;
import com.rewardapp.backend.models.Contribution;
import com.rewardapp.backend.models.ContributionDetails;
import com.rewardapp.backend.models.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContributionService {
    private final ContributionDAO contributionDAO;
    private final ContributionDetailsDAO contributionDetailsDAO;

    public ContributionDetails get(Long id) {
        return contributionDetailsDAO.getContributionDetails(id)
                .orElseThrow(() -> new RuntimeException("Contribution not found."));
    }

    public Contribution save(Contribution contribution, Session session) {
        contribution.setUserId(session.getUserId());
        contributionDAO.validate(contribution);
        return contributionDAO.save(contribution);
    }

    public List<Contribution> getContributionByUserId(Long id) {
        return contributionDAO.getContributionsByUserId(id);
    }
}

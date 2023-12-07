package com.rewardapp.backend;

import com.rewardapp.backend.dao.ContributionDetailsDAO;
import com.rewardapp.backend.models.ContributionDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ContributionDetailsDAOtest {
    @Autowired
    ContributionDetailsDAO contributionDetailsDAO;

    @Test
    void getContributionDetails() {
        ContributionDetails contributionDetails = contributionDetailsDAO.getContributionDetails(12L).orElseThrow();
        Assert.notNull(contributionDetails, "ContributionModel is null");
        Assert.notNull(contributionDetails.getRecyclingCenter(), "RecyclingCenterModel is null");
        Assert.notNull(contributionDetails.getRecyclingCenter().getLocation(), "LocationModel is null");
        Assert.notNull(contributionDetails.getUser(), "User is null");
        Assert.notNull(contributionDetails.getRecyclingCenter().getMaterials(), "Materials is null");
        Assert.notNull(contributionDetails.getRecyclingCenter().getStartTime(), "StartTime is null");
        Assert.notNull(contributionDetails.getRecyclingCenter().getEndTime(), "EndTime is null");
        Assert.notNull(contributionDetails.getTimestamp(), "Timestamp is null");
        Assert.notNull(contributionDetails.getMaterial(), "Material is null");
        Assert.notNull(contributionDetails.getMeasurement(), "Measurement is null");
        Assert.notNull(contributionDetails.getQuantity(), "Quantity is null");
        Assert.notNull(contributionDetails.getReward(), "Reward is null");
        System.out.println(contributionDetails.toString());

    }

}

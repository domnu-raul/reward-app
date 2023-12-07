package com.rewardapp.backend;

import com.rewardapp.backend.dao.ContributionModelDAO;
import com.rewardapp.backend.models.ContributionModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ContributionModelDAOtest {
    @Autowired
    ContributionModelDAO contributionModelDAO;

    @Test
    void getContributionDetails() {
        ContributionModel contributionModel = contributionModelDAO.getContributionDetails(12L);
        Assert.notNull(contributionModel, "ContributionModel is null");
        Assert.notNull(contributionModel.getRecyclingCenter(), "RecyclingCenterModel is null");
        Assert.notNull(contributionModel.getRecyclingCenter().getLocation(), "LocationModel is null");
        Assert.notNull(contributionModel.getUserModel(), "User is null");
        Assert.notNull(contributionModel.getRecyclingCenter().getMaterials(), "Materials is null");
        Assert.notNull(contributionModel.getRecyclingCenter().getStartTime(), "StartTime is null");
        Assert.notNull(contributionModel.getRecyclingCenter().getEndTime(), "EndTime is null");
        Assert.notNull(contributionModel.getTimestamp(), "Timestamp is null");
        Assert.notNull(contributionModel.getMaterial(), "Material is null");
        Assert.notNull(contributionModel.getMeasurement(), "Measurement is null");
        Assert.notNull(contributionModel.getQuantity(), "Quantity is null");
        Assert.notNull(contributionModel.getReward(), "Reward is null");
        System.out.println(contributionModel.toString());

    }

}

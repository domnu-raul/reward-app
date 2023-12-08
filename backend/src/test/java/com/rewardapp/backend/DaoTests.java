package com.rewardapp.backend;

import com.rewardapp.backend.dao.ContributionDAO;
import com.rewardapp.backend.dao.RecyclingCenterDAO;
import com.rewardapp.backend.dao.SessionDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DaoTests {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserService userService;
    @Autowired
    ContributionDAO contributionDAO;
    @Autowired
    RecyclingCenterDAO recyclingCenterDAO;
    @Autowired
    SessionDAO sessionDAO;

}

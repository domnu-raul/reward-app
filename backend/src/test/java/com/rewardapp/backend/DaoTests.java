package com.rewardapp.backend;

import com.rewardapp.backend.dao.ContributionDAO;
import com.rewardapp.backend.dao.RecyclingCenterDAO;
import com.rewardapp.backend.dao.SessionDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.Contribution;
import com.rewardapp.backend.entities.RecyclingCenter;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserModel;
import com.rewardapp.backend.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    @Test
    void selectAllUsers() {
        List<UserModel> l = userDAO.list();
        for (var x : l
        ) {
            System.out.println(x);

        }
    }

    @Test
    void getRecyclingCenter() {
        RecyclingCenter rc = recyclingCenterDAO.getRecyclingCenterById(653L);
        System.out.println(rc);
    }

    @Test
    void setVerified() {
        Long uId = 103L;
        Assert.assertEquals(true, userDAO.setVerified(uId));
    }

    @Test
    void getByUsername() {
        UserModel userModel = userDAO.getUserByUsername("adin");
        Assert.assertEquals((Long) 103L, userModel.getId());
    }

    @Test
    void insertUser() {
//        User user = new User();
//        user.setUsername("zbcakzz");
//        user.setEmail("01222a222222222");
//
//        userDAO.save(user);
//        user = userDAO.getUserByUsername("zbckzz");
//        System.out.println(user);
    }

    @Test
    void deleteUnverified() {
        List<Long> ids = Arrays.asList(new Long[]{102L, 2L});
        Integer amount = userDAO.deleteUnverifiedUsersFrom(ids);
        Assert.assertEquals((Integer) 2, amount);
    }

    @Test
    void registerUser() {
        UserCredentials uc = new UserCredentials("slaszh", "azaaa@ddb.ccc", "1234");
        UserModel userModel = userService.register(uc);

        Assert.assertEquals(uc.username(), userModel.getUsername());
        Assert.assertEquals(uc.email(), userModel.getEmail());
    }

    @Test
    void saveContribution() {
        System.out.println(
                contributionDAO.save(Contribution.builder()
                        .userId(152L)
                        .quantity(10D)
                        .measurement(Contribution.MeasurementType.PIECE)
                        .materialId(5L)
                        .recyclingCenterId(553L)
                        .build())
        );
    }

    @Test
    void sessionDAO() {
        Session session = Session.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(103L)
                .build();

        var s = sessionDAO.save(session);
        System.out.println(s);
    }

}

package com.rewardapp.backend;

import com.rewardapp.backend.dao.ContributionDAO;
import com.rewardapp.backend.dao.RecyclingCenterDAO;
import com.rewardapp.backend.dao.SessionDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.UserCredentials;
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
        List<User> l = userDAO.list();
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
        User user = userDAO.getUserByUsername("adin");
        Assert.assertEquals((Long) 103L, user.getId());
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
//        User user = userService.register(uc);

        Assert.assertEquals(uc.username(), user.getUsername());
        Assert.assertEquals(uc.email(), user.getEmail());
    }

    @Test
    void saveContribution() {
//        System.out.println(
//                contributionDAO.save( new Contribution(
//                        103L,
//                        653L,
//                        1L,
//                        1.0,
//                        "kg",
//                        1L
//                ))
//        );
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

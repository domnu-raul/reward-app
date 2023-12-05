package com.rewardapp.backend;

import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.entities.dto.UserCredentials;
import com.rewardapp.backend.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ServiceTests {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserService userService;

    @Test
    void selectAllUsers() {
        List<User> l = userDAO.list();
        for (var x : l
        ) {
            System.out.println(x);

        }
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
        User user = new User();
        user.setUsername("zbck");
        user.setEmail("0");

        userDAO.save(user);
        user = userDAO.getUserByUsername("zbck");
        System.out.println(user);
    }

    @Test
    void deleteUnverified() {
        List<Long> ids = Arrays.asList(new Long[]{102L, 2L});
        Integer amount = userDAO.deleteUnverifiedUsersFrom(ids);
        Assert.assertEquals((Integer) 2, amount);
    }

    @Test
    void registerUser() {
        UserCredentials uc = new UserCredentials("slash", "aaaa@ddb.ccc", "1234");
        User user = userService.registerInternal(uc);

        Assert.assertEquals(uc.username(), user.getUsername());
        Assert.assertEquals(uc.email(), user.getEmail());
    }
}

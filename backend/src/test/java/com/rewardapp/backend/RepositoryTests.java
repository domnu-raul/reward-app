package com.rewardapp.backend;

import com.rewardapp.backend.entities.RecyclingCenterMaterial;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.repositories.MaterialRepository;
import com.rewardapp.backend.repositories.RecyclingCenterRepository;
import com.rewardapp.backend.repositories.SessionRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RepositoryTests {
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    RecyclingCenterRepository recyclingCenterRepository;

    @Test
    void sessionJdbcEvent() {
        Session session = new Session(null, null, null, 102L);
        session = sessionRepository.save(session);
        Assert.assertNotEquals(null, session.getSessionId());
        Assert.assertNotEquals(null, session.getExpirationDate());
        Assert.assertEquals((Long) 102L, session.getUserId());
    }

    @Test
    void setRecyclingCenterRepository() {
        var m = recyclingCenterRepository.getRecyclingCenterById(602L);
        System.out.println(m);
        for (var m_id : m.getMaterials()
                .stream()
                .map(RecyclingCenterMaterial::getMaterial)
                .toList()) {
            System.out.println(m_id);
        }
    }
}

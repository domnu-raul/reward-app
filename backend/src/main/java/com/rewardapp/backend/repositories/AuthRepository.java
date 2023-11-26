package com.rewardapp.backend.repositories;

import com.rewardapp.backend.dao.InternalUserJdbcDAO;
import com.rewardapp.backend.models.InternalUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepository {
    private final InternalUserJdbcDAO internalUserJdbcDAO;

    public AuthRepository(InternalUserJdbcDAO internalUserJdbcDAO) {
        this.internalUserJdbcDAO = internalUserJdbcDAO;
    }

    public InternalUser register(InternalUser user) {
        internalUserJdbcDAO.create(user);

        Optional<InternalUser> optionalUser = internalUserJdbcDAO.get(user.getUsername());
        if (optionalUser.isEmpty())
            throw new RuntimeException("Failed to create user");

        return optionalUser.get();
    }
}

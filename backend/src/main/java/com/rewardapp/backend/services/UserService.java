package com.rewardapp.backend.services;

import com.rewardapp.backend.exceptions.AuthException;
import com.rewardapp.backend.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    public User validate(String email, String password) throws AuthException {
        return null;
    }

    public User register(String username, String email, String password) throws AuthException {
        return null;
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.exceptions.AuthException;
import com.rewardapp.backend.models.User;
import com.rewardapp.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User validate(String email, String password) throws AuthException {
        return null;
    }

    public Integer register(String username, String email, String password) throws AuthException {
        return this.repository.register(username, email, password);
    }

    public User find_by_id(Integer id) {
        return this.repository.find_by_id(id);
    }
}

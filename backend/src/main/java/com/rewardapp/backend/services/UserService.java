package com.rewardapp.backend.services;

import com.rewardapp.backend.models.UserModel;
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

    public UserModel register(UserModel user) {
        return this.repository.register(user);
    }

    public UserModel find_by_id(Integer id) {
        return this.repository.find_by_id(id);
    }

    public UserModel find_by_username(String username) {
        return this.repository.find_by_username(username);
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.models.User;
import com.rewardapp.backend.repositories.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User validate(String email, String password) {
        return null;
    }

    public User register(User user) {
        return this.repository.register(user);
    }

    public User find_by_id(Integer id) {
        return this.repository.find_by_id(id);
    }
}

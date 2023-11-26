package com.rewardapp.backend.services;

import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.User;
import com.rewardapp.backend.repositories.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionService {
    private final SessionRepository repository;

    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

    public Session find_by_id(Integer id) {
        //todo
        //return this.repository.find_by_id(id);
        return null;
    }

    public Session create(User user) {
        return repository.create(user);
    }
}

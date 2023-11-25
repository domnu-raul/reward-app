package com.rewardapp.backend.services;

import com.rewardapp.backend.models.SessionModel;
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

    public SessionModel find_by_id(Integer id) {
        return this.repository.find_by_id(id);
    }
}

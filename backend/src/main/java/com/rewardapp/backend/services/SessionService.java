package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.repositories.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class SessionService {
    private final SessionRepository repository;

    public SessionService(SessionRepository repositoryJPA) {
        this.repository = repositoryJPA;
    }

    public Session find_by_id(Integer id) {
        //todo
        //return this.repository.find_by_id(id);
        return null;
    }

    public Session create(User user) {
        Session session = new Session();
        session.setSession_id(UUID.randomUUID().toString());
        session.setUser_id(user.getId());
        return this.repository.save(session);
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.repositories.SessionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class SessionService {
    private final SessionRepository repository;

    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

    public Session create(User user) {
        Session session = new Session();
        session.setUserId(user.getId());
        return repository.save(session);
    }

    public Session validate(String sessionId) {
        Optional<Session> optionalSession = repository.findSessionBySessionId(sessionId);
        if (optionalSession.isPresent())
            return optionalSession.get();

        return null;
    }

    public void logout(String sessionId) {
        repository.removeSessionBySessionId(sessionId);
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * MON-SUN")
    public void purgeSessions() {
        repository.removeSessionsByExpirationDateBefore(Timestamp.valueOf(LocalDateTime.now()));
    }
}

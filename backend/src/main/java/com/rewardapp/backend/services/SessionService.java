package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.repositories.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    
    public Session validateRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            throw new RuntimeException("Empty cookie jar.");

        Optional<String> sessionIdOptional = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("session_id"))
                .map(Cookie::getValue)
                .findAny();

        if (sessionIdOptional.isEmpty())
            throw new RuntimeException("You must be logged in.");

        String sessionId = sessionIdOptional.get();

        Optional<Session> optionalSession = repository.findSessionBySessionId(sessionId);
        if (optionalSession.isPresent())
            return optionalSession.get();

        return null;
    }
    
    public void logout(String sessionId) {
        repository.removeSessionBySessionId(sessionId);
    }
}

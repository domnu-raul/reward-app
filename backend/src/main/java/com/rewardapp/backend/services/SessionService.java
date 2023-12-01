package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.repositories.SessionRepository;
import com.rewardapp.backend.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
public class SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    public Session create(User user) {
        Session session = new Session();
        session.setUserId(user.getId());
        return sessionRepository.save(session);
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

        Optional<Session> optionalSession = sessionRepository.findSessionBySessionId(sessionId);
        if (optionalSession.isEmpty())
            throw new RuntimeException("Invalid session.");

        return optionalSession.get();
    }

    public Session validateAdminRequest(HttpServletRequest request) {
        Session session = validateRequest(request);
        User user = userRepository.getUserById(session.getUserId());

        if (user.getType() == User.UserType.user)
            throw new RuntimeException("Unauthorized.");

        return session;
    }
    
    public void logout(String sessionId) {
        sessionRepository.removeSessionBySessionId(sessionId);
    }
}

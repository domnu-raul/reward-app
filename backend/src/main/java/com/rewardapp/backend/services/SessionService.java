package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.SessionDAO;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {
    private final SessionDAO sessionDAO;

    public Session save(User user) {
        Session session = new Session();
        session.setSessionId(UUID.randomUUID().toString());
        session.setUserId(user.getId());

        return sessionDAO.save(session);
    }

    public Session validateRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            throw new RuntimeException("Empty cookie jar.");

        Optional<String> sessionIdOptional = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("session_id"))
                .map(Cookie::getValue)
                .findAny();

        String sessionId = sessionIdOptional
                .orElseThrow(() -> new RuntimeException("You must be logged in."));

        return sessionDAO.findSessionBySessionId(sessionId)
                .orElseThrow();
    }

    public void logout(Session session) {
        sessionDAO.removeSessionBySessionId(session.getSessionId());
    }
}

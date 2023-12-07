package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.SessionDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.models.UserModel;
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
    private final UserDAO userDAO;

    public Session create(UserModel userModel) {
        Session session = Session.builder()
                .userId(userModel.getId())
                .sessionId(UUID.randomUUID().toString())
                .build();

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

        Session session = sessionDAO.getSessionBySessionId(sessionId);

        return session;
    }

    public Session validateAdminRequest(HttpServletRequest request) {
        Session session = validateRequest(request);
        UserModel userModel = userDAO.getUserById(session.getUserId());

        if (userModel.getType() == UserModel.UserType.USER)
            throw new RuntimeException("Unauthorized.");

        return session;
    }

    public void logout(Session session) {
        sessionDAO.removeSessionBySessionId(session.getSessionId());
    }
}

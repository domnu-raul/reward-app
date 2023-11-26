package com.rewardapp.backend.repositories;

import com.rewardapp.backend.dao.SessionJdbcDAO;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository {
    private final SessionJdbcDAO jdbcDAO;

    public SessionRepository(SessionJdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    public Session find_by_session_id(String session_id) {
        Optional<Session> optionalSessionModel = jdbcDAO.get(session_id);
        if (optionalSessionModel.isEmpty())
            throw new RuntimeException("session doesn't exist");

        return optionalSessionModel.get();
    }

    public Session create(User user) {
        Session session = new Session();
        String session_id = UUID.randomUUID().toString();

        session.setSession_id(session_id);
        session.setUser_id(user.getId());

        jdbcDAO.create(session);

        Optional<Session> optionalSessionModel = jdbcDAO.get(session_id);
        if (optionalSessionModel.isEmpty())
            throw new RuntimeException("failed to create session;");

        return optionalSessionModel.get();
    }
}

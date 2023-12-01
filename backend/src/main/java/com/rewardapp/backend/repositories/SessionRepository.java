package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    public Session getSessionById(Integer id);
    public Optional<Session> findSessionBySessionId(String sessionId);

    @Transactional
    public Session removeSessionBySessionId(String sessionId);

    @Transactional
    public List<Session> removeSessionsByExpirationDateBefore(Timestamp timestamp);
}

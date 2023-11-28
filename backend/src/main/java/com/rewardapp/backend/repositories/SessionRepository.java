package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    public Session getSessionById(Integer id);
}

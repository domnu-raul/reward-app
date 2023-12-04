package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {

    @Transactional
    public List<EmailToken> removeEmailTokenByExpirationDateBefore(Timestamp timestamp);

    public Optional<EmailToken> findEmailTokenByToken(String token);
}

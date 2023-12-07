package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.EmailTokenDAO;
import com.rewardapp.backend.entities.EmailToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailTokenService {
    private final EmailTokenDAO emailTokenDAO;

    public EmailToken create(Long userId) {
        EmailToken token = EmailToken.builder()
                .token(UUID.randomUUID().toString())
                .userId(userId)
                .build();

        return emailTokenDAO.save(token);
    }

    public Optional<EmailToken> findEmailByToken(String emailToken) {
        return emailTokenDAO.findByToken(emailToken);
    }
}

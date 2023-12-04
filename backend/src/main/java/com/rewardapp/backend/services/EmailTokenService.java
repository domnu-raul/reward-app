package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.repositories.EmailTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmailTokenService {
    private final EmailTokenRepository repository;

    public EmailTokenService(EmailTokenRepository repository) {
        this.repository = repository;
    }

    public EmailToken create(Long userId) {
        EmailToken token = new EmailToken();
        token.setUserId(userId);
        return repository.save(token);
    }

    public Optional<EmailToken> findEmailByToken(String emailToken) {
        return repository.findEmailTokenByToken(emailToken);
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.repositories.EmailTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class EmailTokenService {
    private final EmailTokenRepository repository;
    public EmailTokenService(EmailTokenRepository repository) {
        this.repository = repository;
    }

    public void create(Long userId) {
        EmailToken token = new EmailToken();
        token.setUserId(userId);
        repository.save(token);
    }
}

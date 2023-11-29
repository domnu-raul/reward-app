package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.repositories.EmailTokenRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Long> purgeTokens() {
        // returns all the distinct id's of the users who had email tokens.
        return repository
                .removeEmailTokenByExpirationDateBefore(Timestamp.valueOf(LocalDateTime.now()))
                .stream()
                .map(x -> x.getUserId())
                .distinct()
                .collect(Collectors.toList());
    };


}

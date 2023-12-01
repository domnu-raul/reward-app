package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.repositories.EmailTokenRepository;
import com.rewardapp.backend.repositories.SessionRepository;
import com.rewardapp.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {
    private final EmailTokenRepository emailTokenRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);

    private MaintenanceService(EmailTokenRepository emailTokenRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        this.emailTokenRepository = emailTokenRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Scheduled(cron = "0 0 0 * * MON-SUN")
    private void purgeUnverifiedUsers() {
        logger.info("Scheduler started task \"purgeUnverifiedUsers()\":");

        List<EmailToken> emailTokens = emailTokenRepository
                .removeEmailTokenByExpirationDateBefore(Timestamp.valueOf(LocalDateTime.now()));

        logger.info(
                String.format("Deleted %d expired email tokens.", emailTokens.size())
        );

        List<User> users = userRepository
                .deleteUsersByIdInAndVerified(emailTokens
                        .stream()
                        .map(x -> x.getUserId())
                        .distinct()
                        .collect(Collectors.toList()),
                        false);

        logger.info(
                String.format("Deleted %d unverified users.", users.size())
        );

        logger.info("Scheduler task \"purgeUnverifiedUsers()\" finalized.");
    }

    @Scheduled(cron = "0 0 0 * * MON-SUN")
    public void purgeSessions() {
        logger.info("Scheduler started task \"purgeUnverifiedUsers()\":");
        List<Session> sessions = sessionRepository.removeSessionsByExpirationDateBefore(Timestamp.valueOf(LocalDateTime.now()));
    }
}

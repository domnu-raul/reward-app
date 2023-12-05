package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.repositories.EmailTokenRepository;
import com.rewardapp.backend.repositories.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceService {
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);
    private final EmailTokenRepository emailTokenRepository;
    private final UserDAO userDAO;
    private final SessionRepository sessionRepository;

    private MaintenanceService(EmailTokenRepository emailTokenRepository, UserDAO userDAO, SessionRepository sessionRepository) {
        this.emailTokenRepository = emailTokenRepository;
        this.userDAO = userDAO;
        this.sessionRepository = sessionRepository;
    }

    @Scheduled(cron = "0 0 0 * * MON-SUN")
    @Scheduled(initialDelay = 1L)
    private void purgeUnverifiedUsers() {
        logger.info("Scheduler started task \"purgeUnverifiedUsers()\":");

        List<EmailToken> emailTokens = emailTokenRepository
                .removeEmailTokenByExpirationDateBefore(Timestamp.valueOf(LocalDateTime.now()));

        logger.info(
                String.format("Deleted %d expired email tokens.", emailTokens.size())
        );

        Integer deleted_users = userDAO.deleteUnverifiedUsersFrom(emailTokens
                .stream()
                .map(x -> x.getUserId())
                .distinct()
                .toList());

        logger.info(
                String.format("Deleted %d unverified users.", deleted_users)
        );

        logger.info("Scheduler task \"purgeUnverifiedUsers()\" finalized.");
    }

    @Scheduled(cron = "0 0 0 * * MON-SUN")
    public void purgeSessions() {
        logger.info("Scheduler started task \"purgeUnverifiedUsers()\":");
        List<Session> sessions = sessionRepository.removeSessionsByExpirationDateBefore(Timestamp.valueOf(LocalDateTime.now()));
    }
}

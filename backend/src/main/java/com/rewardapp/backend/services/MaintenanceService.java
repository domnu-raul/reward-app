package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.EmailTokenDAO;
import com.rewardapp.backend.dao.SessionDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.EmailToken;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);
    private final EmailTokenDAO emailTokenDAO;
    private final UserDAO userDAO;
    private final SessionDAO sessionDAO;

    @Scheduled(cron = "0 0 0 * * MON-SUN")
    @Scheduled(initialDelay = 1L)
    private void purgeUnverifiedUsers() {
        logger.info("Scheduler started task \"purgeUnverifiedUsers()\":");

        List<EmailToken> emailTokens = emailTokenDAO.removeExpiredTokens();

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
        logger.info("Scheduler started task \"purgeSessions()\":");
        sessionDAO.removeExpiredSessions();
    }
}

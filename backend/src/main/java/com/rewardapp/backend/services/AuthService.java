package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.InternalUserJdbcDAO;
import com.rewardapp.backend.dao.SessionJdbcDAO;
import com.rewardapp.backend.models.InternalUser;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.repositories.AuthRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class AuthService {
    private final SessionService sessionService;
    private final InternalUserJdbcDAO internalUserDAO;
    private final SessionJdbcDAO sessionDAO;

    private final AuthRepository repository;

    public AuthService(SessionService sessionService, InternalUserJdbcDAO internalUserDAO, SessionJdbcDAO sessionDAO, AuthRepository repository) {
        this.sessionService = sessionService;
        this.internalUserDAO = internalUserDAO;
        this.sessionDAO = sessionDAO;
        this.repository = repository;
    }

    public Session login_validation(InternalUser user) {
        Optional<InternalUser> userModelOptional = this.internalUserDAO.get(user.getUsername());
        // todo: separate concerns into service and repository;
        if (userModelOptional.isEmpty())
            throw new RuntimeException("user doesn't exist");

        InternalUser userModel = userModelOptional.get();

        if (!BCrypt.checkpw(user.getPassword(), userModel.getPassword()))
            throw new RuntimeException("incorrect password");

        return sessionService.create(userModel);
    }

    public InternalUser register(InternalUser user) {
        Pattern email_validation = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        if (!email_validation.matcher(user.getEmail()).matches())
            throw new RuntimeException("invalid e-mail address");

        String salt = BCrypt.gensalt();
        String hashed_password = BCrypt.hashpw(user.getPassword(), salt);

        user.setPassword(hashed_password);

        return this.repository.register(user);
    }
}

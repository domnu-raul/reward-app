package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.InternalUserDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.InternalUser;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.entities.dto.UserCredentials;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {
    private final UserDAO userDAO;
    private final InternalUserDAO internalUserDAO;

    public UserService(UserDAO userDAO, InternalUserDAO internalUserDAO) {
        this.userDAO = userDAO;
        this.internalUserDAO = internalUserDAO;
    }

    public User validateInternal(UserCredentials credentials) {
        User user = userDAO.getUserByUsername(credentials.username());
        InternalUser internalUser = internalUserDAO.getInternalUserById(user.getId());

        if (BCrypt.checkpw(credentials.password(), internalUser.getPassword()))
            return user;

        throw new RuntimeException("Incorrect password.");

    }

    public User registerInternal(UserCredentials credentials) {
        Pattern email_validation = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        if (!email_validation.matcher(credentials.email()).matches())
            throw new RuntimeException("invalid e-mail address");

        String salt = BCrypt.gensalt();
        String hashed_password = BCrypt.hashpw(credentials.password(), salt);

        User user = new User();

        user.setUsername(credentials.username());
        user.setEmail(credentials.email());

        userDAO.save(user);
        user = userDAO.getUserByUsername(credentials.username());

        InternalUser internalUser = new InternalUser();

        internalUser.setId(user.getId());
        internalUser.setPassword(hashed_password);
        internalUserDAO.save(internalUser);

        return user;
    }

    public User setVerified(Long userId) {
        userDAO.setVerified(userId);
        return userDAO.getUserById(userId);
    }

    public User getUserByUsername(String username) {
        return this.userDAO.getUserByUsername(username);
    }

    public User getUserById(Long id) {
        return this.userDAO.getUserById(id);
    }
}

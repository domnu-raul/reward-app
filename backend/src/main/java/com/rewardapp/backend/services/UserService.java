package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.InternalUserDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.dao.UserDetailsDAO;
import com.rewardapp.backend.entities.InternalUser;
import com.rewardapp.backend.models.User;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserDetails;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final InternalUserDAO internalUserDAO;
    private final UserDetailsDAO userDetailsDAO;

    public User validate(UserCredentials credentials) {
        User user = userDAO.getUserByUsername(credentials.username());
        InternalUser internalUser = internalUserDAO.getInternalUserById(user.getId());

        if (BCrypt.checkpw(credentials.password(), internalUser.getPassword()))
            return user;
        else
            throw new RuntimeException("Incorrect password.");
    }

    public User register(UserCredentials credentials) {
        Pattern email_validation = Pattern.compile(
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        if (!email_validation.matcher(credentials.email()).matches())
            throw new RuntimeException("invalid e-mail address");

        String salt = BCrypt.gensalt();
        String hashed_password = BCrypt.hashpw(credentials.password(), salt);

        User user = new User();
        user.setUsername(credentials.username());
        user.setEmail(credentials.email());

        user = userDAO.save(user);

        InternalUser internalUser = new InternalUser(user.getId(), hashed_password);

        internalUserDAO.save(internalUser);
        return user;
    }

    public User setVerified(Long userId) {
        return userDAO.setVerified(userId);
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    public UserDetails getUserDetailsById(Long id) {
        return userDetailsDAO.getUserDetails(id);
    }

    public User updateUser(Long id, UserCredentials userCredentials) {
        return userDAO.updateUser(id, userCredentials);
    }
}

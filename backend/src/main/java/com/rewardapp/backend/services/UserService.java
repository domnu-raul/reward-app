package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.InternalUserDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.InternalUser;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final InternalUserDAO internalUserDAO;

    public UserModel validate(UserCredentials credentials) {
        UserModel userModel = userDAO.getUserByUsername(credentials.username());
        InternalUser internalUser = internalUserDAO.getInternalUserById(userModel.getId());

        if (BCrypt.checkpw(credentials.password(), internalUser.getPassword()))
            return userModel;
        else
            throw new RuntimeException("Incorrect password.");
    }

    public UserModel register(UserCredentials credentials) {
        Pattern email_validation = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        if (!email_validation.matcher(credentials.email()).matches())
            throw new RuntimeException("invalid e-mail address");

        String salt = BCrypt.gensalt();
        String hashed_password = BCrypt.hashpw(credentials.password(), salt);

        UserModel userModel = new UserModel();
        userModel.setUsername(credentials.username());
        userModel.setEmail(credentials.email());

        userModel = userDAO.save(userModel);

        InternalUser internalUser = InternalUser.builder()
                .id(userModel.getId())
                .password(hashed_password)
                .build();

        internalUserDAO.save(internalUser);

        return userModel;
    }

    public UserModel setVerified(Long userId) {
        userDAO.setVerified(userId);
        return userDAO.getUserById(userId);
    }

    public UserModel getUserByUsername(String username) {
        return this.userDAO.getUserByUsername(username);
    }

    public UserModel getUserById(Long id) {
        return this.userDAO.getUserById(id);
    }
}

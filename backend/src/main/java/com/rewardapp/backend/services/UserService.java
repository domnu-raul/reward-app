package com.rewardapp.backend.services;

import com.rewardapp.backend.dao.InternalUserDAO;
import com.rewardapp.backend.dao.UserDAO;
import com.rewardapp.backend.entities.InternalUser;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserModel;
import com.rewardapp.backend.models.assemblers.UserModelAssembler;
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
    private final UserModelAssembler userModelAssembler;

    public User validate(UserCredentials credentials) {
        User user = userDAO.getUserByUsername(credentials.username());
        InternalUser internalUser = internalUserDAO.getInternalUserById(user.getId());

        if (BCrypt.checkpw(credentials.password(), internalUser.getPassword()))
            return user;
        else
            throw new RuntimeException("Incorrect password.");
    }

    public UserModel register(UserCredentials credentials) {
        Pattern email_validation = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        if (!email_validation.matcher(credentials.email()).matches())
            throw new RuntimeException("invalid e-mail address");

        String salt = BCrypt.gensalt();
        String hashed_password = BCrypt.hashpw(credentials.password(), salt);

        User user = new User();
        user.setUsername(credentials.username());
        user.setEmail(credentials.email());

        user = userDAO.save(user);

        InternalUser internalUser = InternalUser.builder()
                .id(user.getId())
                .password(hashed_password)
                .build();

        internalUserDAO.save(internalUser);

        return userModelAssembler.toModel(user);
    }

    public UserModel setVerified(Long userId) {
        User user = userDAO.setVerified(userId);
        return userModelAssembler.toModel(user);
    }

    public UserModel getUserByUsername(String username) {
        User user = userDAO.getUserByUsername(username);
        return userModelAssembler.toModel(user);
    }

    public UserModel getUserById(Long id) {
        User user = userDAO.getUserById(id);
        return userModelAssembler.toModel(user);
    }
}

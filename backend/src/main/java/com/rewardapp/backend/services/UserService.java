package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.InternalUser;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.repositories.InternalUserRepository;
import com.rewardapp.backend.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final InternalUserRepository internalUserRepository;

    public UserService(UserRepository userRepository, InternalUserRepository internalUserRepository) {
        this.userRepository = userRepository;
        this.internalUserRepository = internalUserRepository;
    }

    public User validate(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        InternalUser internalUser = internalUserRepository.getInternalUserById(user.getId());

        if (BCrypt.checkpw(password, internalUser.getPassword()))
            return user;

        throw new RuntimeException("Incorrect password.");

    }

    public User register(String username, String email, String password) {
        Pattern email_validation = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        if (!email_validation.matcher(email).matches())
            throw new RuntimeException("invalid e-mail address");

        String salt = BCrypt.gensalt();
        String hashed_password = BCrypt.hashpw(password, salt);

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);

        this.userRepository.save(user);
        InternalUser internalUser = new InternalUser();

        internalUser.setId(user.getId());
        internalUser.setPassword(hashed_password);
        internalUserRepository.save(internalUser);

        return user;
    }

    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    public User getUserById(Long id) {
        return this.userRepository.getUserById(id);
    }
}

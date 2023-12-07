package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.entities.Session;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final SessionService sessionService;
    private final EmailSenderService emailSenderService;
    private final EmailTokenService emailTokenService;

    public Session login(UserCredentials userCredentials) {
        UserModel userModel = userService.validate(userCredentials);
        Session session = sessionService.create(userModel);

        return session;
    }

    public Session validateRequest(HttpServletRequest request) {
        return sessionService.validateRequest(request);
    }

    public UserModel register(UserCredentials userCredentials) {
        UserModel userModel = userService.register(userCredentials);
//        emailSenderService.sendEmail(user.getEmail(), "Verify your email", "http://localhost:8080/api/auth/verify/" + emailTokenService.create(user.getId()).getToken());

        return userModel;
    }

    //todo: create a model for the oauth request body
    public UserModel registerOAuth(UserCredentials userCredentials) {
        return null;
    }

    public UserModel verifyEmail(String token) {
        Optional<EmailToken> emailToken = emailTokenService.findEmailByToken(token);
        if (emailToken.isEmpty())
            throw new RuntimeException("Invalid token.");

        return userService.setVerified(emailToken.get().getUserId());
    }

    public UserModel getUser(Session session) {
        return userService.getUserById(session.getUserId());
    }

    //todo: implement a status representation for the logout
    public void logout(Session session) {
        sessionService.logout(session);

    }

    public void logoutAll(UserModel userModel) {
//        sessionService.logoutAll(user);
    }

    public UserModel changePassword(UserModel userModel, String newPassword) {
        return null;
    }

    public UserModel changeEmail(UserModel userModel, String newEmail) {
        return null;
    }

    public UserModel changeUsername(UserModel userModel, String newUsername) {
        return null;
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.entities.EmailToken;
import com.rewardapp.backend.entities.User;
import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.models.UserModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final SessionService sessionService;
    private final EmailSenderService emailSenderService;
    private final EmailTokenService emailTokenService;

    public Session login(UserCredentials userCredentials, HttpServletResponse httpResponse) {
        User user = userService.validate(userCredentials);
        Session session = sessionService.create(user);

        Cookie cookie = new Cookie("session_id", session.getSessionId());
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        httpResponse.addCookie(cookie);

        return session;
    }

    public Session validateRequest(HttpServletRequest request) {
        return sessionService.validateRequest(request);
    }

    public Session validateAdminRequest(HttpServletRequest request) {
        Session session = sessionService.validateRequest(request);
        UserModel user = userService.getUserById(session.getUserId());
        if (!Objects.equals(user.getType(), User.UserType.ADMIN.toString())) {
            throw new RuntimeException("You must be an admin to perform this action.");
        }
        return session;
    }

    public UserModel register(UserCredentials userCredentials) {
        UserModel user = userService.register(userCredentials);
//        emailSenderService.sendEmail(user.getEmail(), "Verify your email", "http://localhost:8080/api/auth/verify/" + emailTokenService.create(user.getId()).getToken());

        return user;
    }

    //todo: create a model for the oauth request body
    public User registerOAuth(UserCredentials userCredentials) {
        return null;
    }

    public UserModel verifyEmail(String token) {
        EmailToken emailToken = emailTokenService.findEmailByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token."));

        return userService.setVerified(emailToken.getUserId());
    }

    public UserModel getUser(Session session) {
        return userService.getUserById(session.getUserId());
    }

    //todo: implement a status representation for the logout
    public void logout(Session session) {
        sessionService.logout(session);

    }

    public void logoutAll(User user) {
//        sessionService.logoutAll(user);
    }

    public UserModel changePassword(User user, String newPassword) {
        return null;
    }

    public UserModel changeEmail(User user, String newEmail) {
        return null;
    }

    public UserModel changeUsername(User user, String newUsername) {
        return null;
    }
}

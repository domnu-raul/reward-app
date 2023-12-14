package com.rewardapp.backend.controllers;

import com.rewardapp.backend.models.Session;
import com.rewardapp.backend.models.User;
import com.rewardapp.backend.models.UserCredentials;
import com.rewardapp.backend.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<Session> login(@RequestBody UserCredentials credentials,
                                       HttpServletResponse httpResponse) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(authService.login(credentials, httpResponse));
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(
      @RequestBody UserCredentials credentials) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(authService.register(credentials));
  }

  @PatchMapping("/verify/{email_token}")
  public ResponseEntity<User>
  verifyEmail(@PathVariable("email_token") String token) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(authService.verifyEmail(token));
  }

  @DeleteMapping("/logout")
  public ResponseEntity<Object> logout(HttpServletRequest request) {
    Session session = authService.validateRequest(request);
    authService.logout(session);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping("/logout-all")
  public ResponseEntity<Object> logoutAll(HttpServletRequest request) {
    Session session = authService.validateRequest(request);
    authService.logoutAll(session);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PatchMapping("/update")
  public ResponseEntity<User>
  update(@RequestBody UserCredentials userCredentials,
         HttpServletRequest request) {

    Session session = authService.validateRequest(request);

    return ResponseEntity.status(HttpStatus.OK)
        .body(authService.updateUser(session.getUserId(), userCredentials));
  }
}

package com.rewardapp.backend.models;

public record UserResponse (Integer id, String username, String email, Boolean verified, String register_date, String type) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.id, user.username, user.email, user.verified, user.register_date, user.type);
    }
}

package com.rewardapp.backend.services;

import com.rewardapp.backend.models.User;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;
}

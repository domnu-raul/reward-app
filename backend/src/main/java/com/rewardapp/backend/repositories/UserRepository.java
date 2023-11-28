package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getUserById(Long id);
    public User getUserByUsername(String username);
}

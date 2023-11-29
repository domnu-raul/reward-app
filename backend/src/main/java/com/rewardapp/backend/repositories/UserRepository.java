package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getUserById(Long id);
    public User getUserByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM User WHERE verified = false  AND id IN :ids")
    public void purgeUnverifiedUsers(List<Long> ids);
}
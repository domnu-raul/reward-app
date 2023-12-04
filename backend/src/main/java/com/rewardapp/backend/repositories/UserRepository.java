package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserById(Long id);

    public User getUserByUsername(String username);

    @Modifying
    @Query("UPDATE users SET verified = true WHERE id = :id")
    public void setVerified(Long id);

    @Modifying
    @Transactional
    public List<User> deleteUsersByIdInAndVerified(List<Long> ids, Boolean verified);
}
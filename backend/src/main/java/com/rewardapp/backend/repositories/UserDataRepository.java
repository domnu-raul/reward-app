package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    public UserData getUserDataByUserId(Long id);

}

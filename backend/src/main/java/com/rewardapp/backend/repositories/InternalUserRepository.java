package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalUserRepository extends JpaRepository<InternalUser, Long> {
    public InternalUser getInternalUserById(Long id);
}

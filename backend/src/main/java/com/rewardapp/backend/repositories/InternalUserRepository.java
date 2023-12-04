package com.rewardapp.backend.repositories;

import com.rewardapp.backend.entities.InternalUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalUserRepository extends CrudRepository<InternalUser, Long> {
    public InternalUser getInternalUserById(Long id);
}

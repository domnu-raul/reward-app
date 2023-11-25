package com.rewardapp.backend.repositories;

import com.rewardapp.backend.models.SessionModel;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {
    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM sessions" +
            "WHERE ID = ?;";
    public SessionModel find_by_id(Integer id) {

    }
}

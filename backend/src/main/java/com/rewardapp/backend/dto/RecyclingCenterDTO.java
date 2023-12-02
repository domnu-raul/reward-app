package com.rewardapp.backend.dto;

import java.sql.Time;
import java.util.List;

public record RecyclingCenterDTO (
        Long id,
        String name,
        List<String>materials,
        LocationDTO location,
        Time startingTime,
        Time endTime
){}

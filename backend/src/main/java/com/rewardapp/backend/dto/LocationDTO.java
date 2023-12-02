package com.rewardapp.backend.dto;

public record LocationDTO(
        String county,
        String city,
        String address,
        String zipcode,
        Double longitude,
        Double latitude
) {}

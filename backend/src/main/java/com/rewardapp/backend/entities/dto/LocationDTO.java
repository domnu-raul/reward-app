package com.rewardapp.backend.entities.dto;

public record LocationDTO(
        String county,
        String city,
        String address,
        String zipcode,
        Double longitude,
        Double latitude
) {
}

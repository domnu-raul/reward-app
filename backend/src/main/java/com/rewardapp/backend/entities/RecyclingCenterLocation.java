package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recycling_centers_locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecyclingCenterLocation {
    @Id
    @Column("recycling_center_id")
    private Long id;
    @Column("county")
    private String county;
    @Column("city")
    private String city;
    @Column("address")
    private String address;
    @Column("zipcode")
    private String zipcode;
    @Column("longitude")
    private Double longitude;
    @Column("latitude")
    private Double latitude;
    @Transient
    private RecyclingCenter recyclingCenter;
}
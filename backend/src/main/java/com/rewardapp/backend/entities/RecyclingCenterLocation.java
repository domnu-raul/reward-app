package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("recycling_centers_locations")
@Data
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

    //    @OneToOne
//    @JoinColumn(name = "recycling_center_id")
//    @MapsId
    private RecyclingCenter recyclingCenter;
}
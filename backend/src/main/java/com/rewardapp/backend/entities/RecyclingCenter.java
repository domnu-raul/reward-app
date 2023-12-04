package com.rewardapp.backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;
import java.util.List;

@Table("recycling_centers")
@Data
public class RecyclingCenter {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;

    //    @OneToMany(mappedBy = "recyclingCenter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RecyclingCenterMaterial> recyclingCenterMaterials;
    //    @OneToOne(mappedBy = "recyclingCenter", cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
    private RecyclingCenterLocation recyclingCenterLocation;
    @Column("start_time")
    private Time startingTime;
    @Column("end_time")
    private Time endTime;
}
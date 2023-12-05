package com.rewardapp.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Time;
import java.util.List;

@Table("recycling_centers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecyclingCenter {
    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Transient
    private List<RecyclingCenterMaterial> recyclingCenterMaterials;
    @Column("recycling_center_id")
    private RecyclingCenterLocation recyclingCenterLocation;
    @Column("start_time")
    private Time startingTime;
    @Column("end_time")
    private Time endTime;
    @MappedCollection(idColumn = "recycling_center_id", keyColumn = "material_id")
    private List<RecyclingCenterMaterial> materials;
}